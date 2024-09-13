import odrive
from odrive.enums import *
import time
import os

kg_to_current = 1.0 / 1.8
logging = False
log_file = None
desired_sample_rate = 60
sample_interval = 1.0 / desired_sample_rate 

def find_odrive():
    print("Finding an ODrive...")
    while True:
        try:
            motor = odrive.find_any(timeout=0.1)
            print("ODrive found!")
            return motor
        except:
            pass

# Funktionen för att logga data
def log_data(motor):
    global log_file, start_time

    if log_file is None:
        log_file_name = get_logfile_name()
        log_file = open(log_file_name, "w")
        log_file.write("Time (s);Position;Applied Force (kg)\n")  # CSV-header

    elapsed_time = time.perf_counter() - start_time
    position = motor.axis1.encoder.pos_estimate
    applied_force = motor.axis1.motor.current_control.Iq_measured / kg_to_current

    log_file.write(f"{elapsed_time:.4f};{position};{applied_force:.4f}\n")
    print(f"Logged data: Time: {elapsed_time:.4f}s, Position: {position}, Applied Force: {applied_force:.4f}kg")

# Håll reda på hur många loggfiler som redan finns
def get_logfile_name():
    base_name = "motor_data_log"
    extension = ".csv"
    i = 1
    while os.path.exists(f"{base_name}{i}{extension}"):
        i += 1
    return f"{base_name}{i}{extension}"

# Huvudprogram som körs utan GUI
def main():
    global logging, start_time

    motor = find_odrive()

    # Börja logga data
    logging = True
    start_time = time.perf_counter()

    # Sätt kraften till 5 kg
    motor.axis1.motor.config.current_lim = 10 * kg_to_current
    print("Set applied force to 5 kg")

    next_sample_time = time.perf_counter()

    while logging:
        current_time = time.perf_counter()

        # Kontrollera om det är dags att ta ett nytt prov
        if current_time >= next_sample_time:
            log_data(motor)
            next_sample_time += sample_interval  # Ställ in nästa samplingstid

        # Vänta en mycket kort stund för att undvika att slösa CPU-cykler
        #time.sleep(0.001)  # Finjustera CPU-användning utan att påverka precisionen

if __name__ == "__main__":
    main()
