# python-virus_decrypt

**_STEP ONE_**

First mission is to decrypt the HuRAP code into a human readable form. In order to accomplish this, need to proceed with the following steps:
•HuRAP will be given as the first command-line argument in a textfile(for example ashurap.txt). The given 0s and 1s are actually binary representations of different characters according to SHuCKSCII(alternate to ASCII) and it will be provided as the second command-line argument also in a text file in a tab-delimited format as shown below:

CHAR  HEXVALUE  DESCRIPTION

So in order to find the normal character representation of HuRAP, first need to convert it to its hexadecimal representation and then to its normal character representation by looking the obtained hexadecimal values up in the SHuCKSCII table. For instance, let the first 16 digits of HuRAP 0111001101110101, which when converted to the hexadecimal system correspond to 7375. When look these values up in the SHuCKSCII table, will be found that for the given sample table above these values correspond  to','(comma) and'*'(asterisk) characters respectively. Need to perform this operation on the whole input file (except for the special line which starts with a character other than 0 or 1, and needs to be excluded) to get its hexadecimal representation.

Once done with the conversion of HuRAP to its character representation, the characters still will not make any sense! This is because code has been shifted by a certain value before being converted to binary values. Hence, will need to decrypt the obtained code to get the real deal. The shift encryption algorithm works as follows

It is a type of substitution cipher in which each letter in the original text is ’shifted’ a certain number of places down or up the alphabet. For example, with a shift of 1, A would be replaced by B, B would become C, etc.  Here, the shift amount is called the Key. For an alphabet in which there are N letters, encryption would look like this: 

encryptedchar = (originalchar + shiftamount) mod N 

Decryption, on the other hand, is performed as:originalchar = (encryptedchar - shiftamount) mod N

Mission is to decrypt the obtained character representation of HuRAP and get the original version of the program. The alphabet on which your decryption function will operate contains all characters given in the SHuCKSCII table,in the order starting from the top position and ending at the last character in the table when read from the first column of the shuckscii.txt file (this means that ’letters’ in this alphabet will not only be standard letters, but also numbers and all kinds of special characters). The key (shift amount) will be given as a 2’s complement binary number in the special line inhurap.txt. When removed the special character (it can be any char other than 0 or 1) from the beginning of the line, will get a string of 0s and 1s(of arbitrary length) that can directly convert to a decimal value to obtain the key(which can be any integer, positive or negative)

**_STEP TWO_**

Once have obtained the original version of the HuRAP, it is time to write the virus which will alter the original code, so that any computer or device executing it will not harm any humans, but terminate themselves instead. For this mission, will be given a list of strings that need to be substituted, and the corresponding virus strings that will replace them. This information will be given as the third command-line argument in a colon-separated text file(for example viruscode.txt) with the following format:

STRINGTOBESUBSTITUTED<:>VIRUSSTRINGA 

Your second mission is to write a virus which plants the virus strings instead of certain key strings (let’s call them killer strings) in the original code that are responsible for turning the machines into killers. For this part of mission need to be careful about the following:

• Have to search every line of the original code for every given killer string, and if a killer string is found replace it with its virus counterpart
• Every line may have 0 or more killer strings in it. All of them need to be replaced
• When virus encounters a killer string within a line, only that string should be replaced by its counterpart virus string, and no other character should be changed within the line
• The killer words are case sensitive:  if ’kill’ is a killer word, the words ’Kill’ or ’KILL’ are not

**_STEP THREE_**

For this part of the mission, expected to finish virus program, so that it will convert the final version of virus-infected HuRAP produced in Mission 01 to the expected sequence of 0s and 1s, so that deactivated virus code won't be noticed by coders of the virus. In order to accomplish this, need to take the following steps:

• First need to encrypt the code using the described encryption scheme of the shift cipher and the same key that was used for decryption in Mission 00
• Then use the SHuCKSCII table to convert the encrypted code to its  hexadecimal representation
• Finally, convert the hexadecimal representation into the corresponding binary representation. All steps should produce output with results.


