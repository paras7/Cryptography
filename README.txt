UTEID: pa6633
FIRSTNAME: Paras
LASTNAME: Arora
CSACCOUNT: paras
EMAIL: paras.k.arora@gmail.com

[Program 3]
[Description]
I only have one java file with only class in it to keep it straightforward. The code can basically be described as first I see wether the user is trying to encode the message by looking at whether he/she entered -E or -D if they are trying to decode. Depending on which it goes to different if/else if statement and does the corresponding work. To compile my program, you simply do: javac *.java. To run my program for encoding, you do: java Steganography -E image.bmp my-message. For decoding, you do: java Steganography -D image-steg.bmp my-message-out.

[Finish]
- Finished all requirements.

[Questions&Answers]

[Question 1]
Comparing your original and modified images carefully, can you detect *any* difference visually (that is, in the appearance of the image)? 

[Answer 1]
-No, they look alike.


[Question 2]
Can you think of other ways you might hide the message in image files (or in other types of files)?

[Answer 2]
- Do it by storing the message starting bottom right and go up and left.


[Question 3]
Can you invent ways to increase the bandwidth of the channel?

[Answer 3]
- Use more than just the last bit of RGB.



[Question 4]
Suppose you were tasked to build an "image firewall" that would block images containing hidden messages. Could you do it? How might you approach this problem?

[Answer 4]
- Yeah, I think it possible to do it if I changed the last 2 bits of RGBs, this would theoratically destroy any message encoded in the image and prove to be a good "image firewall".



[Question 5]
Does this fit our definition of a covert channel? Explain your answer.

[Answer 5]
- The definition of Covert Channel can be defined as: "A hidden communications channel on a system that allows for the bypassing of the system security policy" and this encoding of message in the image is definitely that because you are using the image as a channel to hide information and communicate.