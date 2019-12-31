# Blackjack Card Counter

## Project Goal: To create a blackjack simulator and an agent with a card counting strategy

This project was a fun exercise to explore an interest of mine in the use of card counting as a strategy to beat the popular table game blackjack. 
The project itself can be broken down into two main parts: a blackjack simulator and an agent which plays the game based on a basic strategy in conjunction with a card counting strategy.
In the end, the agent was able to achieve a consistent profit over ~810,000 hands played.

### Blackjack simulator

The blackjack simulator encodes all the basic rules, mechanics, and decisions for blackjack. 
For the purposes of this project, I implemented a simple version of blackjack which favours my agent, and likely does not exist in any casino or friend's party game:
- Dealer stands on soft 17
- Blackjack plays 3:2
- Player can only hit, stand, or double down (no splits or surrenders)
- There is no reshuffling of the deck; cards are played and discarded until there are less than 10 cards left in the shoe
- There are no limits on bet sizes and no-one watching for telltale signs of card counting
Additionally, the simulator can take any number of decks in a shoe, set the minimum bet to any number, and define the number of rounds (shoes to play)

### Agent

When playing the game, the agent follows a basic strategy optimised for single-deck blackjack where the dealer stands on soft 17, which is encoded in single_deck_strategy.py
Also, the agent keeps a running count of all the cards that have been dealt, and increases its bet size based on a certain threshold for the count. The exact threshold number can be changed and experimented with, and largely depends on the counting strategy used.
I have included two basic counting strategies: the Hi-Lo count and Thorp's Ten-Count strategy.

## How to use

You can set the parameters for the blackjack simulator in the main:
```
num_rounds = 10000
num_decks = 1
min_bet = 1
```

Choose which card counting strategy to use by importing from the respective files (and commenting out the other):
```
#from hi_lo_count import card_count
from ten_count import card_count
```

You can adjust the agent's reaction to the card count on lines 83-84, and tweak the threshold and bet size to achieve the highest profit:
```
# place bet - betting logic
display += 'count = ' + str(running_count) + '\n'
if running_count > 30:
    bet = running_count*min_bet*10
else:
    bet = min_bet
display += 'bet = ' + str(bet) + '\n'
```

Toggle display of every hand played, showing how each game plays out in text form (line 176):
```
print(display)
```

## Results
Using 100,000 rounds (~810,000 hands) to test the long term overall performance of the strategy: 
- Player wins ~43.5% of the time
- Dealer wins ~48.3% of the time
- A tie occurs ~8.2% of the time

### Ten Count
Using Thorp's Ten Count strategy under the following parameters:
- min_bet  = 1
- num_decks = 1
- num_rounds = 100000
- Increase bet size when count > 30
- Increased bet = running_count * 10
    
Despite losing more than the dealer, the agent manages to consistently return a profit over 100,000 rounds. However the profit/loss ranges from 300-500k, which is a significant distribution considering the huge sample size. The bet sizes ranged from $1 up to ~$700 due to a high running_count number.

### Hi-Lo
Using the Hi-Lo strategy strategy under the following parameters:
- min_bet  = 1
- num_decks = 1
- num_rounds = 100000
- Increase bet size when count > 5
- Increased bet = running_count * 10

As with the Ten-Count strategy, the agent also consistently returns a profit over 100,000 rounds, with the profit/loss ranging from 40-50k. The reason for the difference between the two strategy payoffs can be attributed to the much lower betting pattern from $1 to ~$200, but can be scaled accordingly to achieve the same levels of profit.

### Note:
These parameters are not indicative of the optimal values to use to achieve the highest or most consistent payoff, rather they have simply been chosen as an example demonstrate the program.

## N.B.
While card counting is not illegal, it is disliked by casinos and thus discouraged.
This project is not intended to aid anyone in card counting, rather it is an exploration into the concept of card counting and beating the odds. Realistically, the use of constant reshuffling, multiple decks, as well as people watching for betting patterns that indicate card counting will prevent people fron using such card counting strategies. The limitations of this blackjack simulation means that applying the algorithm to real life will likely lead to lost money. 

## Improvements and todo
- Incorporating the card count into the basic strategy (i.e. deciding which action to take) instead of only bet sizing should yield better results
- Different card counting strategies may have more success
- Optimising the strategy for multiple decks (6 or 8)
- Encode splits and surrenders
- The implementation can be cleaned up, optimise performance (string concatenation)

## License and contributing
MIT License.
Feel free to contribute, give feedback, or use however you like.


