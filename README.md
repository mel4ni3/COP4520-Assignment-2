# COP4520-Assignment-2

## Problem 1: Minotaur's Birthday Party

### Problem statement:

The Minotaur invited N guests to his birthday party. When the guests arrived, he made 
the following announcement. 
The guests may enter his labyrinth, one at a time and only when he invites them to do 
so. At the end of the labyrinth, the Minotaur placed a birthday cupcake on a plate. When 
a guest finds a way out of the labyrinth, he or she may decide to eat the birthday 
cupcake or leave it. If the cupcake is eaten by the previous guest, the next guest will find 
the cupcake plate empty and may request another cupcake by asking the Minotaur’s 
servants. When the servants bring a new cupcake the guest may decide to eat it or leave 
it on the plate. 
The Minotaur’s only request for each guest is to not talk to the other guests about her or 
his visit to the labyrinth after the game has started. The guests are allowed to come up 
with a strategy prior to the beginning of the game. There are many birthday cupcakes, so 
the Minotaur may pick the same guests multiple times and ask them to enter the 
labyrinth. Before the party is over, the Minotaur wants to know if all of his guests have 
had the chance to enter his labyrinth. To do so, the guests must announce that they have 
all visited the labyrinth at least once. 
Now the guests must come up with a strategy to let the Minotaur know that every guest 
entered the Minotaur’s labyrinth. It is known that there is already a birthday cupcake left 
at the labyrinth’s exit at the start of the game. How would the guests do this and not 
disappoint his generous and a bit temperamental host? 
Create a program to simulate the winning strategy (protocol) where each guest is 
represented by one running thread. In your program you can choose a concrete number 
for N or ask the user to specify N at the start. 

### How to run:

Navigate to the directory where the program files are located, then run the following commands in the command line:

```
javac p1.java
java p1
```

### Explanation:

In this program, a thread is created for each guest to the party and then started. Threads are created in an array of threads, and the first thread at index 0 serves as a counter of visited guests and replaces the cupcake. If there is no cupcake in the labyrinth, that means a new guest ate a cupcake and the counter increments, and the minotaur's servant brings out another cupcake.
The threads other than the one at index 0 each represent a guest. These guests will each eat a cupcake if they haven't eaten already, otherwise, if they ate already, they will visit the labyrinth but not eat. Guests keep going to the labyrinth until every guest has eaten a cupcake at least once, which is known when the counter by thread 0 reaches the total number of guests.

### Experimental Evaluation:

This program usually takes around 17 ms with 50 guests, 57 ms for 100 guests and 235 ms for 200 guests. Data racing is used such that the threads for guests are constantly competing to go inside the labyrinth, but only one can go in at a time. The guests enter the labyrinth in random order, and this occurs until the number of unique guests that ate a cupcake and visited the labyrinth
is equal to the number of total guests. Because all the guests are competing to enter, a lock is used so that only one guest can enter the labyrinth at a time. This is locked when a guest enters and unlocked when they leave. This way, all guests try to go in for more concurrent efficiency and only one enters at a time.

## Problem 2: Minotaur's Crystal Vase

### Problem statement:

The Minotaur decided to show his favorite crystal vase to his guests in a dedicated 
showroom with a single door. He did not want many guests to gather around the vase 
and accidentally break it. For this reason, he would allow only one guest at a time into 
the showroom. He asked his guests to choose from one of three possible strategies for 
viewing the Minotaur’s favorite crystal vase: 
1) Any guest could stop by and check whether the showroom’s door is open at any time 
and try to enter the room. While this would allow the guests to roam around the castle 
and enjoy the party, this strategy may also cause large crowds of eager guests to gather 
around the door. A particular guest wanting to see the vase would also have no 
guarantee that she or he will be able to do so and when. 
2) The Minotaur’s second strategy allowed the guests to place a sign on the door 
indicating when the showroom is available. The sign would read “AVAILABLE” or 
“BUSY.” Every guest is responsible to set the sign to “BUSY” when entering the 
showroom and back to “AVAILABLE” upon exit. That way guests would not bother trying 
to go to the showroom if it is not available. 
3) The third strategy would allow the quests to line in a queue. Every guest exiting the 
room was responsible to notify the guest standing in front of the queue that the 
showroom is available. Guests were allowed to queue multiple times. 
Which of these three strategies should the guests choose? Please discuss the advantages 
and disadvantages. 
Implement the strategy/protocol of your choice where each guest is represented by 1 
running thread. You can choose a concrete number for the number of guests or ask the 
user to specify it at the start. 

### How to run:

Navigate to the directory where the programs are located, then run the following commands in the command line:

```
javac p2.java
java.p2
```

### Explanation:

Option 2 is used. In this program, there is a thread created for each guest within an array of threads. At the start of the program, the showroom sign on the door is available and the busy boolean is set as false. The threads constantly try to go inside the vase showroom. However, only one is allowed at a time. When visitors go in, they change the sign on the showroom door to busy. This is represented bty the busy boolean being set to true when a guest enters the showroom.
Threads inside the showroom sleep for a predetermined time to simulate viewing the vase and then the guest in the showroom sets the sign to available, making the busy boolean be false. Each guest has a chance that they will want to visit the crystal vase. Chance is an integer that is either 0 or 1. The guest will view the vase if chance is 1, and chance starts at 1 ior all guests.
Each time a guest views the vase, chance is randomly calculated for if they will want to view the vase again. The program ends when every guest has seen the vase at least once. Every time a unique guest sees the vase, the visited guests counter increments.

### Experimental Evaluation:

This program usually takes around 8 seconds for 50 guests, 25 seconds for 100 guests, and 81 seconds for 200 guests. Randomness is used in the runtime because the program can take longer if the same guests visit the vase multiple times, if their chance value stays as 1. However, this program is efficient because guests only try to view the vase if the showroom is available, as opposed to option 1 where guests constantly try to see te vase. This makes it go by faster because this deters large crowds or threads from going into the room. Option 3 is less efficient because while the queue would make sure everyone goes in at least once, it would take a long time for guests in the back of the line to view the vase.
With this option, there is no line and guests already know if the room is available or not.