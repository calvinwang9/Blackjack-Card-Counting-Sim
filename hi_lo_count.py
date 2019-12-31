
# Hi-Lo card counting strategy
def card_count(card):
    if card[0] == 'J' or card[0] == 'Q' or card[0] == 'K' or card[0] == 10:
        return -1
    elif card[0] == 'A':
        return -1
    elif int(card[0]) < 7:
        return 1
    return 0
