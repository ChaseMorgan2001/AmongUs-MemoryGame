# AmongUs-MemoryGame
This is a 5x5 memory card game. There are 12 matching pairs and one card that does not match. When clicked, this card is revealed at no penalty. 
The game starts with all positions on the board blank, the “guesses made” counter at 0
and the “matches made” counter at 0. Play is as follows:
• The player makes a guess by clicking with the mouse on one grid element (card). The game
should toggle that grid to reveal the image it hides.
• If that pattern is the card with no match, the “guesses made” counter is NOT incremented, the card is
revealed (it has no match), and play continues.
• The player revealed a card with a match, the player must make a second guess by clicking on
another square with the mouse. If the second square reveals a match with the first square, the
squares are permanently displayed (remain unhidden and are no longer responsive to clicks) and
the “matches made” counter is incremented. 
• If the two selected squares do not match, both are toggle back to the hidden state
• The “number of guesses” counter is incremented by 1
• The game ends when all grid elements are revealed

The 12 sets are the 12 different colors available in the game Among Us, and the odd card is the Report Button.
