
# thorp's ten-count counting strategy
def card_count(card):
    if card[0] == 'J' or card[0] == 'Q' or card[0] == 'K' or card[0] == 10:
        return -9
    else:
        return 4