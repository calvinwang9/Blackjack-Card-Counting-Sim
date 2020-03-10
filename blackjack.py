import random
from single_deck_strategy import player_strategy
from hi_lo_count import card_count
#from ten_count import card_count


card_type = ['♠', '♥', '♣', '♦']
card_value = ['A', 2, 3, 4, 5, 6, 7, 8, 9, 10, 'J', 'Q', 'K']

class newHand:
    def __init__(self):
        self.value = 0
        self.hard = True
        self.bust = False
        self.cards = []
    
    def calc_value(self):
        # add card totals
        self.value = 0
        for card in self.cards:
            if card[0] == 'A':
                self.value += 11
                self.hard = False
            elif card[0] == 'J' or card[0] == 'Q' or card[0] == 'K':
                self.value += 10
            else:
                self.value += card[0]
        
        # if we have a soft hand and total is over 21, treat ace as 1 (hard hand)
        if self.hard == False and self.value > 21:
            self.hard = True
            self.value -= 10
            
        if self.value > 21:
            self.bust = True
    
    def add_card(self, card):
        self.cards.append(card)
        self.calc_value()
        
    def count(self):
        total = 0
        for card in self.cards:
            total += card_count(card)
        return total
        
        
def init_deck():
    deck = []
    for i in card_value:
        for j in card_type:
            deck.append([i,j])
    return deck


def play_game(num_rounds, num_decks, min_bet):

    # initalise variables
    player_score = 0
    dealer_score = 0
    hands_played = 0
    cash = 0
    
    for _ in range(num_rounds):
        # initialise shuffled deck
        single_deck = init_deck()
        deck = single_deck
        for _ in range(num_decks-1):
            deck = deck + single_deck
        random.shuffle(deck)
        
        hand = 0
        running_count = 0
        # cut card at 10 cards
        display = ''
        while len(deck) > 10:
            hand += 1
            display += '--------Hand ' + str(hand) + '--------\n'
            hands_played += 1
            
            # place bet - betting logic
            display += 'count = ' + str(running_count) + '\n'
            if running_count > 5:
                bet = running_count*min_bet*10
            else:
                bet = min_bet
            display += 'bet = ' + str(bet) + '\n'
            
            # deal initial cards
            player_hand = newHand()
            dealer_hand = newHand()
            
            player_hand.add_card(deck.pop(0))
            dealer_hand.add_card(deck.pop(0))
            player_hand.add_card(deck.pop(0))
            dealer_hand.add_card(deck.pop(0))
            
            # update card count
            running_count += player_hand.count()
            running_count += dealer_hand.count()
            
            display += ('dealer\t?  ' + str(dealer_hand.cards[1][0]) + str(dealer_hand.cards[1][1]) + ' = ?\n')
            display += ('player\t' + str(player_hand.cards[0][0]) + str(player_hand.cards[0][1]) + ' ' 
                            + str(player_hand.cards[1][0]) + str(player_hand.cards[1][1])
                            + ' = ' + str(player_hand.value) + '\n')
            
            result = 'pending'
            
            # player turn
            if player_hand.value == 21:
                display += ('\ndealer\t' + str(dealer_hand.cards[0][0]) + str(dealer_hand.cards[0][1]) + ' ' 
                            + str(dealer_hand.cards[1][0]) + str(dealer_hand.cards[1][1])
                            + ' = ' + str(dealer_hand.value) + '\n')
                if dealer_hand.value == 21:
                    result = 'push'
                else:
                    result = 'player blackjack'
                    player_score += 1
                    cash += bet * 1.5
            else:
                display = display + '\nplayer turn\n'
                action = player_strategy(player_hand, dealer_hand.cards[1][0])
                while action != 'stand':
                    card = deck.pop(0)
                    player_hand.add_card(card)
                    running_count += card_count(card)
                    if action == 'double':
                        display += 'player double ' + str(card[0]) + card[1] + '\ttotal = ' + str(player_hand.value) + '\n'
                        bet = 2*bet
                    else:
                        display += 'player hit ' + str(card[0]) + card[1] + '\ttotal = ' + str(player_hand.value) + '\n'                  
                    if player_hand.bust == True:
                        result = 'player bust'
                        dealer_score += 1
                        cash -= bet
                        break
                    if action == 'double':
                        break
                    else:
                        action = player_strategy(player_hand, dealer_hand.cards[1][0])
            
            # dealer turn
            if result == 'pending':
                display += ('player stands\n\ndealer turn\ndealer\t' 
                            + str(dealer_hand.cards[0][0]) + str(dealer_hand.cards[0][1]) + ' ' 
                            + str(dealer_hand.cards[1][0]) + str(dealer_hand.cards[1][1])
                            + ' = ' + str(dealer_hand.value) + '\n')
                if dealer_hand.value == 21:
                    result = 'dealer blackjack'
                    dealer_score += 1
                    cash -= bet
                else:
                    while dealer_hand.value < 17:
                        card = deck.pop(0)
                        dealer_hand.add_card(card)
                        running_count += card_count(card)
                        display += 'dealer hit ' + str(card[0]) + card[1] + '\ttotal = ' + str(dealer_hand.value) + '\n'
                        if dealer_hand.bust == True:
                            result = 'dealer bust'
                            player_score += 1
                            cash += bet 
            
            if result == 'pending':
                if player_hand.value > dealer_hand.value:
                    result = 'player win'
                    player_score += 1
                    cash += bet
                elif player_hand.value < dealer_hand.value:
                    result = 'dealer win'
                    dealer_score += 1
                    cash -= bet
                else:
                    result = 'push'    
            display += result + '\n'
        
#        print(display)    
    
    print('\n--------Results--------')
    print('hands played = ' + str(hands_played))
    print('player wins ' + str(round(player_score/hands_played*100, 2)) + '% of the time')
    print('dealer wins ' + str(round(dealer_score/hands_played*100, 2)) + '% of the time')
    print('tie occurs ' + str(round((hands_played - player_score - dealer_score)/hands_played*100, 2)) + '% of the time')
    print('profit/loss = ' + str(cash))


if __name__ == "__main__":
    # adjustable variables
    num_rounds = 100000
    num_decks = 1
    min_bet = 10

    play_game(num_rounds, num_decks, min_bet)