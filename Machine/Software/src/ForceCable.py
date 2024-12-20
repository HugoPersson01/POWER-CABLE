import dearpygui.dearpygui as dpg

import odrive
from odrive.enums import *
import time
import math
import numpy as np

import os

class SineModulation:
    frequency = 0.0
    amplitude = 0.0
    def __init__(self, frequency, amplitude):
        self.amplitude = amplitude
        self.frequency = frequency

    def modulate(self, time, set_point):
        return set_point + (np.sin(time*(2*np.pi*self.frequency))*self.amplitude)

kg_to_current = 1.0/1.8

logging = False

current_position = 0

graph_update_rate = 1.0/60.0

position_data = [1.0]
applied_weight_data = [1.0]

timestart = False

dpg.create_context()
motor = None
print("finding an odrive...")
try:
    motor = odrive.find_any(timeout = 0.1)
except:
    pass    

def calibrate():
    motor.axis1.requested_state = AXIS_STATE_FULL_CALIBRATION_SEQUENCE

def turn_on():
    if(motor.axis1.current_state != AXIS_STATE_CLOSED_LOOP_CONTROL):
        print("AXIS_STATE -> CLOSED_LOOP_CONTROL")
        motor.axis1.requested_state = AXIS_STATE_CLOSED_LOOP_CONTROL
    else:
        print("AXIS_STATE -> IDLE")
        motor.axis1.requested_state = AXIS_STATE_IDLE

def clear_errors():
    motor.clear_errors()

gloabl_kg = 0.0

def set_force_kg(kg):
    global gloabl_kg
    global_kg = kg
    if (calibrated_kg_to_current(kg) <= 0):
        motor.axis1.motor.config.current_lim = 0
    else:
        motor.axis1.motor.config.current_lim = calibrated_kg_to_current(kg)

def move_to(position):
    motor.axis1.controller.input_pos = position

def get_input_position():
    return motor.axis1.controller.input_pos

def get_current_position():
    return motor.axis1.encoder.pos_estimate

def get_max_force():
    return convertCurrentToKg()

def get_current_force():
    return (motor.axis1.motor.current_control.Iq_measured + 0.6)/ kg_to_current

def get_set_force():
    return convertCurrentToKg()   # inversen av den funktionen, byt till x

def set_force_button():
    set_force_kg(dpg.get_value("kg_input_double"))

def update_graphs(time):
    position_data.append(get_current_position())
    applied_weight_data.append(get_current_force())
    if len(position_data) > 500:
        position_slice = position_data[-500:]
    else:
        position_slice = position_data

    if len(applied_weight_data) > 500:
        applied_weight_slice = applied_weight_data[-500:]
    else:
        applied_weight_slice = applied_weight_data

    applied_weight_slice = applied_weight_data[-500:]
    dpg.set_value("position_series", [np.arange(0.0,len(position_slice)),position_slice])
    dpg.set_value("weight_series", [np.arange(0.0,len(applied_weight_slice)),applied_weight_slice])

    dpg.fit_axis_data("y_axis")
    dpg.set_axis_limits_auto("y_axis")
    dpg.fit_axis_data("x_axis")
    dpg.set_axis_limits_auto("x_axis")

# Keep track of amount of current log files
def get_logfile_name():
    base_name = "motor_data_log"
    extension = ".csv"
    i = 1
    while os.path.exists(f"{base_name}{i}{extension}"):
        i += 1
    return f"{base_name}{i}{extension}"

def start_recording():
    global logging, start_time, log_file
    logging = not logging
    start_time = time.time()

    if logging:
        print("Start recording...")
        log_file = open(get_logfile_name(), "w")
        log_file.write("Time (s);Position;Applied Force (kg)\n")  # CSV header
        dpg.set_item_label("start_recording_button", "Stop Recording")
    else:
        print("Stop recording...")
        if log_file is not None:
            log_file.close()
            log_file = None
        dpg.set_item_label("start_recording_button", "Start Recording")

log_file = None
def log_data():
    global log_file, start_time
    
    if log_file is None:
        log_file = open("motor_data_log.csv", "w")
        log_file.write("Time (s);Position;Applied Force (kg)\n")  # CSV header

    # Log current time, position, and applied force
    elapsed_time = time.time() - start_time
    position = get_current_position()
    applied_force = get_current_force()
    log_file.write(f"{elapsed_time:.2f};{position};{applied_force:.4f}\n".replace('.', ',')) # Suitable for Excell

def start_linear():
    global start_time, timestart
    timestart = not timestart
    start_time = time.time()
    
    if timestart:
        print("linear function start...")
        dpg.set_item_label("Linear_check", "Stop function")
    else:
        print("linear function stop...")
        dpg.set_item_label("Linear_check", "Start function")

def calibrated_kg_to_current(inmatatKg):
    new_kg_to_current = ((inmatatKg*kg_to_current) - 0.6)
    return new_kg_to_current

def convertCurrentToKg():
    return ((motor.axis1.motor.config.current_lim + 0.6)/kg_to_current)

def calculate_K():
    start_value = dpg.get_value("Start_value")
    stop_value = dpg.get_value("Stop_value")
    max_time = dpg.get_value("Max_Time")
    # calcuate K
    K = (stop_value - start_value) / max_time
    return K

def run_linear_function(Start_value, Max_Time):
    global start_time
    linear_time = time.time() - start_time
    if (linear_time <= Max_Time):
        motor.axis1.motor.config.current_lim = (((calculate_K() * linear_time) + Start_value) * kg_to_current)   
        print(f"Linear time: {linear_time:.6f} seconds")
 
def time_controll():
    global start_time, timestart
    timestart = not timestart
    if timestart:
        start_time = time.time()
    else:
        pass
    return start_time
    
def move_increment_positive():
    increment = dpg.get_value("move_increment")
    move_increment(increment)

def move_increment_negative():
    increment = dpg.get_value("move_increment")
    move_increment(-increment)

def move_increment(increment):
    motor.axis1.controller.input_pos = motor.axis1.controller.input_pos + (increment/(3*np.pi*(16.0/85.0)))

def update_info(time):
    pos = get_current_position()
    set_weight = get_set_force()
    current_weight = get_current_force()

    dpg.set_value("position_no", "{:.2f}cm".format(pos*3*np.pi*(16.0/85.0)))
    dpg.set_value("set_weight_no", "{:.2f}kg".format(set_weight))
    dpg.set_value("current_weight_no", "{:.2f}kg".format(current_weight))

with dpg.font_registry():
    # first argument ids the path to the .ttf or .otf file
    big_font = dpg.add_font("NotoSerifCJKjp-Medium.otf", 50)
    medium_font = dpg.add_font("NotoSerifCJKjp-Medium.otf", 30)
    small_font = dpg.add_font("NotoSerifCJKjp-Medium.otf", 10)

with dpg.value_registry():
    dpg.add_bool_value(default_value=False, tag="record_position_bool")
    dpg.add_bool_value(default_value=False, tag="record_set_weight_bool")
    dpg.add_bool_value(default_value=False, tag="record_current_weight_bool")
    dpg.add_bool_value(default_value=False, tag="sine_modulation_time_bool")
    dpg.add_bool_value(default_value=False, tag="sine_modulation_distance_bool")
    dpg.add_bool_value(default_value=False, tag="Linear_function_bool")

with dpg.window(label="Reel", tag="reel_window"):
    dpg.add_button(label="+", tag="reel_out_button", callback=move_increment_positive)
    dpg.add_button(label="-", tag="reel_in_button", callback=move_increment_negative)
    dpg.add_input_double(label="(cm)", tag="move_increment", default_value=10.0)

with dpg.window(label="Info", tag="info_window"):
    dpg.add_text(label="Set Weight", tag="set_weight_text")
    dpg.set_value("set_weight_text", "Set Weight")

    set_weight_no = dpg.add_text(label="Set Weight Number", tag="set_weight_no")
    dpg.set_value("set_weight_no", "!!!!")
    dpg.bind_item_font(set_weight_no, medium_font)

    dpg.add_text(label="Current weight", tag="current_weight_text")
    dpg.set_value("current_weight_text", "Current Weight")

    current_weight_no = dpg.add_text(label="Current weight Number", tag="current_weight_no")
    dpg.set_value("current_weight_no", "!!!!")
    dpg.bind_item_font(current_weight_no, medium_font)

    dpg.add_text(label="Position", tag="position_text")
    dpg.set_value("position_text", "position_text")

    position_no = dpg.add_text(label="Position Number", tag="position_no")
    dpg.set_value("position_no", "!!!!")
    dpg.bind_item_font(position_no, medium_font)

with dpg.window(label="Record", tag="record_window"):
    dpg.add_checkbox(label="Position", tag="record_position", source="record_position_bool")
    dpg.add_checkbox(label="Set Weight", tag="record_set_weight", source="record_set_weight_bool")
    dpg.add_checkbox(label="Current Weight", tag="record_current_weight", source="record_current_weight_bool")
    dpg.add_text(label="Sample Rate (Hz)", tag="record_sample_rate_text")
    dpg.set_value("record_sample_rate_text", "Sample Rate (Hz)")

    dpg.add_input_double(label="", tag="record_sample_rate")
    dpg.add_button(label="Start Recording", tag="start_recording_button", callback=start_recording)

with dpg.window(label="Modulation", tag="modulation_window"):
    dpg.add_checkbox(label="Time Sine", tag="sine_modulation_time", source="sine_modulation_time_bool")
    dpg.add_checkbox(label="Position Sine", tag="sine_modulation_distance", source="sine_modulation_distance_bool")
    dpg.add_input_double(label="Freq", tag="sine_frequency", default_value=1.0)
    dpg.add_input_double(label="Min", tag="sine_min", default_value=10.0)
    dpg.add_input_double(label="Max", tag="sine_max", default_value=20.0)

#Linear window
with dpg.window(label="Linear", tag="linear_window"):
    dpg.add_checkbox(label="Start function", tag="Linear_check", callback=start_linear)
    dpg.add_input_double(label="Max Time", tag="Max_Time", default_value=1.0)
    dpg.add_input_double(label="Start Kg", tag="Start_value", default_value=0.0)
    dpg.add_input_double(label="Max Kg", tag="Stop_value", default_value=10.0)

dpg.set_item_pos("info_window", [435, 10])
dpg.set_item_pos("record_window", [650, 10])
dpg.set_item_pos("reel_window", [650, 190])
dpg.set_item_pos("modulation_window", [650, 300])
dpg.set_item_width("info_window", 200)
dpg.set_item_width("record_window", 150)
dpg.set_item_width("reel_window", 150)
dpg.set_item_width("modulation_window", 150)

#Linjär funktion
dpg.set_item_pos("linear_window", [435,240])
dpg.set_item_width("linear_window", 200)

with dpg.window(label="Status", tag="win"):

    with dpg.group(horizontal=True):
        dpg.add_button(label="calibrate", callback=calibrate, tag="calibrate_button")
        dpg.add_button(label="turn on/off", callback=turn_on, tag="turn_on_button")
        dpg.add_button(label="Clear Errors", callback=clear_errors, tag="clear_errors_button")
    with dpg.group(horizontal=True):
        dpg.add_button(label="Set Weight", callback=set_force_button, tag="set_weigth_button")
        dpg.add_input_double(label="Weight (kg)", tag="kg_input_double")

    # create plot
    with dpg.plot(label="Line Series", height=400, width=400):
        # optionally create legend
        dpg.add_plot_legend()

        # REQUIRED: create x and y axes
        dpg.add_plot_axis(dpg.mvXAxis, label="x", tag="x_axis")
        dpg.fit_axis_data("x_axis")
        dpg.set_axis_limits_auto("x_axis")
        dpg.add_plot_axis(dpg.mvYAxis, label="y", tag="y_axis")
        dpg.fit_axis_data("y_axis")
        dpg.set_axis_limits_auto("y_axis")

        # series belong to a y axis
        dpg.add_line_series(np.arange(0.0,len(applied_weight_data)),applied_weight_data, label="Applied Weight", parent="y_axis", tag="weight_series")
        dpg.add_line_series(np.arange(0.0,len(position_data)),position_data, label="Position", parent="y_axis", tag="position_series")

dpg.create_viewport(title='Kraftkabel test', width=835, height=600)
dpg.setup_dearpygui()
dpg.show_viewport()

last_time = 0.0

while(dpg.is_dearpygui_running()):

    current_time = time.time()
    if(current_time > last_time + graph_update_rate) and motor != None:
        update_graphs(current_time)
        update_info(current_time)
        last_time = current_time

    if logging:
        log_data()

    sine_frequency = dpg.get_value("sine_frequency")
    sine_amplitude = (dpg.get_value("sine_max") - dpg.get_value("sine_min"))/2.0
    sine_offset = dpg.get_value("sine_min") + (sine_amplitude)

    if dpg.get_value("sine_modulation_time") and motor != None and sine_frequency > 0.00001:
        sine = SineModulation(sine_frequency, sine_amplitude)
        set_force_kg(sine.modulate(current_time, sine_offset))

    if dpg.get_value("sine_modulation_distance") and motor != None and sine_frequency > 0.00001:
        sine = SineModulation(sine_frequency, sine_amplitude)
        set_force_kg(sine.modulate(get_current_position(), sine_offset))

    #Linear window functions
    Max_Time = dpg.get_value("Max_Time")
    Start_value = dpg.get_value("Start_value")
    Stop_value = dpg.get_value("Stop_value")

    if timestart:
        #dpg.get_value("Linear_check") and motor != None and Max_Time < 40 and Start_value >= 0 and Stop_value <=40 and calculate_K() <= 1: # Möjligtvis max K-värde, kanske 1?
        run_linear_function(Start_value, Max_Time)

    if(motor == None):
        try:
            motor = odrive.find_any(timeout = 0.1)
        except:
            pass
    dpg.render_dearpygui_frame()   
dpg.destroy_context()
