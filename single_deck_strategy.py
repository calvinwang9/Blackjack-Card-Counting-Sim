
# strategy for single deck blackjack dealer stands on soft 17
def player_strategy(player_hand, dealer_card):
    if dealer_card == 'J' or dealer_card == 'Q' or dealer_card == 'K':
        dealer_card = 10
    
    if player_hand.hard == True:
        # hard strategy
        if player_hand.value < 12:
            if player_hand.value == 11:
                return 'double'
            if dealer_card == 'A':
                return 'hit'
            elif player_hand.value == 10 and int(dealer_card) < 10:
                return 'double'
            elif player_hand.value == 9 and int(dealer_card) < 7:
                return 'double'
            elif player_hand.value == 8 and (dealer_card == 5 or dealer_card == 6):
                return 'double'
            return 'hit'
        if player_hand.value == 12:
            if dealer_card == 4 or dealer_card == 5 or dealer_card == 6:
                return 'stand'
            else:
                return 'hit'
        if player_hand.value < 17:
            if dealer_card == 'A':
                return 'hit'
            elif int(dealer_card) > 6:
                return 'hit'
            else:
                return 'stand'
        if player_hand.value >= 17:
            return 'stand'
    else:
        # soft strategy
        if player_hand.value < 18:
            if dealer_card == 4 or dealer_card == 5 or dealer_card == 6:
                return 'double'
            if player_hand.value == 17 and (dealer_card == 2 or dealer_card == 3):
                return 'double'
            return 'hit'
        if player_hand.value == 18 and (dealer_card == 9 or dealer_card == 10):
            return 'hit'
        if dealer_card == 'A':
            return 'stand'
        elif player_hand.value == 18 and int(dealer_card) < 7 and dealer_card != 2:
            return 'double'
        elif player_hand.value == 19 and dealer_card == 6:
            return 'double'
        return 'stand'