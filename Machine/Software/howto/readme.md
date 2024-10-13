# How to use the UserInterface that is implemented in our code:

___ 

#### IMPORTANT: If you don’t feel like reading all of this, we want you to at least read steps 5 and 6 to avoid injuries to yourself or the machine. Get to step 5 by pressing here ➔ [Step 5](#step-5-set-weight).


##### Note: In the pictures shown below, "buttons" and "checkboxes" used in the explained parts will be highlighted in light blue to indicate which buttons to press.

___ 

## Step 1: "Start the program"
> <img src="https://github.com/HugoPersson01/POWER-CABLE/blob/main/Machine/Software/howto/Pictures/Start.png" alt="My Image" width="450" height="300"/>
>
> This is how the interface should look after starting the program.

## Step 2: "Calibrate the machine"
> | Calibrate button | Calibrate in progress |
> |:----------------:|:---------------------:|
> | <img src="https://github.com/HugoPersson01/POWER-CABLE/blob/main/Machine/Software/howto/Pictures/Calibrate.png" alt="My Image" width="450" height="300"/> | <img src="https://github.com/HugoPersson01/POWER-CABLE/blob/main/Machine/Software/howto/Pictures/Calibrate-In-Progress.png" alt="My Image" width="450" height="300"/> |
>
> To calibrate the machine, press the "Calibrate" button shown in the left picture.
> During calibration, the motor will start rotating, pulling the cord inwards and then outwards, as shown in the right picture.
> Notice the red line going down and then back up, this represents the inward and outward movement of the cord.
>
> This is the first instance where you will see a red line and a blue line.
> As indicated in the graph window, the red line represents the positioning of the cord.
> If you pull the cord out, the red line will rise, and if you release the cord, the red line will fall.
> The blue line represents the current weight the motor is applying.
> These two lines provide real-time information that no other training equipment offers.

## Step 2.1: "Clear errors"
> <img src="https://github.com/HugoPersson01/POWER-CABLE/blob/main/Machine/Software/howto/Pictures/Clear-Errors.png" alt="My Image" width="450" height="300"/>
>
> If you calibrate the machine and notice that the calibration did not complete successfully, you will need to clear the errors and restart the calibration process.
> Several reasons may cause the calibration to fail:
> + First fix, ensure that the cable has enough room to move during the calibration process. If this doesn't work try next step.
> + Second fix, check that all cables are properly connected to the encoder, walloutlet or computer. If this also doesn't work try the final step.
> + Third fix, close the program and disconnect the power supply from the wall outlet. Wait for a few seconds before reconnecting the power.
> After performing these checks, press "Clear All Errors," as shown in the picture above, and repeat Step 2.

## Step 4: "Turn on/off"
> <img src="https://github.com/HugoPersson01/POWER-CABLE/blob/main/Machine/Software/howto/Pictures/Turn-On.png" alt="My Image" width="450" height="300"/>
>
> You can turn the machine on and off when ever you want, you will need to turn it on after you have calibrtated the machine.
> When you press the "turn on/off" button it will toggle between being on and off.
> You can easily see if the machine is on or off by looking at the blue line.
> The picture above shows how it looks when you turn the machine on for the first time, the blue line starts of being flat and then starts going up and down.

## Step 5: "Set weight"
> | Chose weight | Set weight |
> |:------------:|:----------:|
> | <img src="https://github.com/HugoPersson01/POWER-CABLE/blob/main/Machine/Software/howto/Pictures/Pick-Weight.png" alt="My Image" width="450" height="300"/> | <img src="https://github.com/HugoPersson01/POWER-CABLE/blob/main/Machine/Software/howto/Pictures/Set-Weight.png" alt="My Image" width="450" height="300"/> |
>
> #### IMPORTANT:
> 
> The left picture above shows where you pick the weight you want to use, we recomande you at the start pick a low weight around 5Kg to make sure you dont harm the mashine or yourself when setting up the machine. Pick the weight you want to use during your training when you are ready to start using the machine. The problems that can occur are:
> + If you change the length of the cord, make it shorter, with the implemented functions (that will be described in step 6) the motor can pull in the cord in a fast way with much power wich can pull down the user if they are holding the cord while doing this.
> + So as we say, we recomand a low weight and a small distance when setting up the machine before training, this will be taken up in step 6, the part where we describe how to change the cords starting length.

## Step 6: "Change cord length"
> <img src="https://github.com/HugoPersson01/POWER-CABLE/blob/main/Machine/Software/howto/Pictures/ChangeDistans.png" alt="My Image" width="450" height="300"/> 
>
> #### IMPORTANT:
> 
> The picture above shows how you can change the reel in/ out distance of the cord. This means how much the cord will reel in/ out every time you press the '+' or '-' button, these buttons will be explained bellow. This is the distance I talked about in step 5. So a more detailed explination of harm the machine can do on the user is if you reel in the cord, lets say 10 cm, and have a heavy starting weight the motor will pull in the cord with a force of the starting weight and it will also do it fast. This can lead to the user being pulled down with a sudden force leading to injury. This might be fixed in a later version of our product, if so this will be updated.
>
> | Reel in the cord | Unreel the cord |
> |:----------------:|:---------------:|
> | <img src="https://github.com/HugoPersson01/POWER-CABLE/blob/main/Machine/Software/howto/Pictures/Decrease-cord.png" alt="My Image" width="450" height="300"/> | <img src="https://github.com/HugoPersson01/POWER-CABLE/blob/main/Machine/Software/howto/Pictures/Extend-Cord.png" alt="My Image" width="450" height="300"/> |
>
> The left picture shows how you can reel in the cord, the cord will reel in with the amount of cm chosen by the user, as we explained above. The right picture shows how you can do unreel the cord, it works the same way as we just explained but the other way around.

## Step 7: "Sine Function"
> <img src="https://github.com/HugoPersson01/POWER-CABLE/blob/main/Machine/Software/howto/Pictures/Sine-Set-Freq.png" alt="My Image" width="450" height="300"/>
> 
> The picture above shows you how to pick a frequancy that will be used to create the sine wave. The frequency 𝑓 tells you how many cycles of the wave occur per second (i.e., how often the wave repeats in one second). The period 𝑇 is the time it takes for the wave to complete one full cycle. Since frequency and
> period are inversely related, you can calculate the period by taking the reciprocal of the frequency, ( 𝑇 = 1 / 𝑓 ).
> + We recomend a low freduancy of atleast 0.25 Hz to get a period time of 4 s, this will ensure safe
> training.
> + If you want a slower sine curve you just need to lower the frecuancy, to like 0.20 to get a period time of 5 s.
> 
> | Set min weight | Set max weight |
> |:----------------:|:---------------:|
> | <img src="https://github.com/HugoPersson01/POWER-CABLE/blob/main/Machine/Software/howto/Pictures/Sine-Set-Min.png" alt="My Image" width="450" height="300"/> | <img src="https://github.com/HugoPersson01/POWER-CABLE/blob/main/Machine/Software/howto/Pictures/Sine-Set-Max.png" alt="My Image" width="450" height="300"/> |
>
> The left picture shows you how to pick a min weight. This will be the minimum value of the sine wave. 
> The right picture shows you how to pick the maximum weight. This will insted collirate to the maximum value of the sine wave.
>
> | Start sine wave function | Sine wave in use |
> |:------------------------:|:----------------:|
> | <img src="https://github.com/HugoPersson01/POWER-CABLE/blob/main/Machine/Software/howto/Pictures/Start-Time-Sine.png" alt="My Image" width="450" height="300"/> | <img src="https://github.com/HugoPersson01/POWER-CABLE/blob/main/Machine/Software/howto/Pictures/Running.png" alt="My Image" width="450" height="300"/> |
> 
>
> The left picture shows you how to start the sine wave function and the right picture shows you how the grafh will look when the sine wave function is in use.
> + The picture bellow explains what all the values (𝑇, min weight, max weight) used to create the sine wave.
>
> <img src="https://github.com/HugoPersson01/POWER-CABLE/blob/main/Machine/Software/howto/Pictures/Sine-Wave-Explain.PNG" alt="My Image" width="400" height="300"/>

