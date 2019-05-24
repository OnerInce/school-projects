import sys

hurap_file = open(sys.argv[1], "r")
schuckscii_file = open(sys.argv[2], "r").read()
virus_codes_file = open(sys.argv[3], "r").read()

# Mission 00: Decrypting the HuRAP

# Dictionary of SCHuCKSII

mydict = {i.split('\t')[1]: i.split('\t')[0] for i in schuckscii_file.split('\n')}

mydict_reverse = {v: k for k, v in mydict.items()}

list_of_alphabet = [i.split('\t')[0] for i in schuckscii_file.split('\n')]

replace_dict = {i.split(':')[0]: i.split(':')[1] for i in virus_codes_file.split('\n')}

length_of_alphabet = len(list_of_alphabet)

# Convert a binary number to Decimal


def bin_to_decimal(binary_number):
    decimal = 0
    power = len(binary_number) - 1
    for i in binary_number:
        decimal += (2 ** power) * int(i)
        power -= 1
    return decimal


# Convert a Decimal number to Hexa

def decimal_to_hexa(decimal_number):
    hex_number = ''
    if decimal_number == 10:
        hex_number += 'A'
    elif decimal_number == 11:
        hex_number += 'B'
    elif decimal_number == 12:
        hex_number += 'C'
    elif decimal_number == 13:
        hex_number += 'D'
    elif decimal_number == 14:
        hex_number += 'E'
    elif decimal_number == 15:
        hex_number += 'F'
    else:
        hex_number += str(decimal_number)
    return hex_number


# Split a whole line of binary to 4 groups of binary

def split_line_to_binary(line):
    line = str(line)
    binary = [line[i:i+4] for i in range(0, len(line), 4)]
    return binary

# Hexas to binary


hexas_to_binary = {'A': '1010', 'B': '1011', 'C': '1100', 'D': '1101', 'E': '1110', 'F': '1111', '9': '1001',
                   '8': '1000', '7': '0111', '6': '0110', '5': '0101', '4': '0100', '3': '0011', '2': '0010',
                   '1': '0001', '0': '0000'}


print("""*********************
     Mission 00 
*********************""", end="\n\n")

print("""--- hex of encrypted code ---
-----------------------------""", end="\n\n")

for i in hurap_file:
    hexaValues = []
    if i[0] is '1' or i[0] is '0':
        list1 = split_line_to_binary(str(i.rstrip()))
        for x in list1:
            hexaValues.append(decimal_to_hexa(bin_to_decimal(x)))
        for p in hexaValues:
            print(p, end='')
        print()
hurap_file.seek(0)

print("""\n--- encrypted code ---
----------------------""", end="\n\n")

for j in hurap_file:
    decimalToHexa = []
    decryptedHexa = []
    if j[0] is '1' or j[0] is '0':
        list2 = split_line_to_binary(str(j.rstrip()))
        for g in list2:
            decimalToHexa.append(decimal_to_hexa(bin_to_decimal(g)))
        decryptedHexa = [a + b for a, b in zip(decimalToHexa[0::2], decimalToHexa[1::2])]
        decryptedHexa = [mydict.get(item) for item in decryptedHexa]
        xxx = ''.join(decryptedHexa)
        print(xxx)
hurap_file.seek(0)

print("""\n--- decrypted code ---
----------------------""", end="\n\n")

# Find the shift amount

for y in hurap_file:
    if y[0] != '1' and y[0] != '0':
        key = y
        key_list = list(key)
        del(key_list[0])
        del(key_list[-1])
        if key_list[0] == '0':
            bbb = ''.join(key_list)
            shift_amount = bin_to_decimal(bbb)
        elif key_list[0] == '1':
            zero_one_change = {'0': '1', '1': '0'}
            list_number = [zero_one_change[x] for x in key_list]
            bbb = ''.join(list_number)
            shift_amount = bin_to_decimal(bbb)
            shift_amount += 1
            shift_amount = -shift_amount
hurap_file.seek(0)

# Shift the characters

for j in hurap_file:
    decimalToHexa = []
    decryptedHexa = []
    if j[0] is '1' or j[0] is '0':
        list2 = split_line_to_binary(str(j.rstrip()))
        for g in list2:
            decimalToHexa.append(decimal_to_hexa(bin_to_decimal(g)))
        decryptedHexa = [a + b for a, b in zip(decimalToHexa[0::2], decimalToHexa[1::2])]
        decryptedHexa = [mydict.get(item) for item in decryptedHexa]
        for index in range(len(decryptedHexa)):
            index_in_alphabet = list_of_alphabet.index(decryptedHexa[index])
            decryptedHexa[index] = list_of_alphabet[(index_in_alphabet - shift_amount) % length_of_alphabet]
        outt = ''.join(decryptedHexa)
        print(outt)
hurap_file.seek(0)


# Mission 01: Coding the virus

print("""\n*********************
     Mission 01 
*********************""", end="\n\n")

global false_count

false_count = 0

for j in hurap_file:
    decimalToHexa = []
    decryptedHexa = []
    if j[0] is '1' or j[0] is '0':
        list2 = split_line_to_binary(str(j.rstrip()))
        for g in list2:
            decimalToHexa.append(decimal_to_hexa(bin_to_decimal(g)))
        decryptedHexa = [a + b for a, b in zip(decimalToHexa[0::2], decimalToHexa[1::2])]
        decryptedHexa = [mydict.get(item) for item in decryptedHexa]
        for index in range(len(decryptedHexa)):
            index_in_alphabet = list_of_alphabet.index(decryptedHexa[index])
            decryptedHexa[index] = list_of_alphabet[(index_in_alphabet - shift_amount) % length_of_alphabet]
        outt = ''.join(decryptedHexa)
        for string in replace_dict.keys():
            if (string in outt) is True:
                outt = outt.replace(string, replace_dict[string])
            elif (string in outt) is False:
                false_count += 1
        if false_count == length_of_alphabet:
            print(outt)
            false_count = 0
        elif false_count < length_of_alphabet:
            print(outt)
            false_count = 0
hurap_file.seek(0)

# Mission 10: Encrypting the virus-infected HuRAP

print("""\n*********************
     Mission 10 
*********************""", end="\n\n")

print("""--- encrypted code ---
----------------------""", end="\n\n")

for j in hurap_file:
    decimalToHexa = []
    decryptedHexa = []
    if j[0] is '1' or j[0] is '0':
        list2 = split_line_to_binary(str(j.rstrip()))
        for g in list2:
            decimalToHexa.append(decimal_to_hexa(bin_to_decimal(g)))
        decryptedHexa = [a + b for a, b in zip(decimalToHexa[0::2], decimalToHexa[1::2])]
        decryptedHexa = [mydict.get(item) for item in decryptedHexa]
        for index in range(len(decryptedHexa)):
            index_in_alphabet = list_of_alphabet.index(decryptedHexa[index])
            decryptedHexa[index] = list_of_alphabet[(index_in_alphabet - shift_amount) % length_of_alphabet]
        outt = ''.join(decryptedHexa)
        for string in replace_dict.keys():
            if (string in outt) is True:
                outt = outt.replace(string, replace_dict[string])
            elif (string in outt) is False:
                false_count += 1
        if false_count == 7:
            false_count = 0
        elif false_count < 7:
            false_count = 0
        out_list = list(outt)
        for letter_index in range(len(out_list)):
            index_in_alphabet_2 = list_of_alphabet.index(out_list[letter_index])
            out_list[letter_index] = list_of_alphabet[(index_in_alphabet_2 + shift_amount) % length_of_alphabet]
        qqq = ''.join(out_list)
        print(qqq)
hurap_file.seek(0)

print("""\n--- hex of encrypted code ---
-----------------------------""", end="\n\n")

for j in hurap_file:
    decimalToHexa = []
    decryptedHexa = []
    final_hexas = []
    if j[0] is '1' or j[0] is '0':
        list2 = split_line_to_binary(str(j.rstrip()))
        for g in list2:
            decimalToHexa.append(decimal_to_hexa(bin_to_decimal(g)))
        decryptedHexa = [a + b for a, b in zip(decimalToHexa[0::2], decimalToHexa[1::2])]
        decryptedHexa = [mydict.get(item) for item in decryptedHexa]
        for index in range(len(decryptedHexa)):
            index_in_alphabet = list_of_alphabet.index(decryptedHexa[index])
            decryptedHexa[index] = list_of_alphabet[(index_in_alphabet - shift_amount) % length_of_alphabet]
        outt = ''.join(decryptedHexa)
        for string in replace_dict.keys():
            if (string in outt) is True:
                outt = outt.replace(string, replace_dict[string])
            elif (string in outt) is False:
                false_count += 1
        if false_count == 7:
            false_count = 0
        elif false_count < 7:
            false_count = 0
        out_list = list(outt)
        for letter_index in range(len(out_list)):
            index_in_alphabet_2 = list_of_alphabet.index(out_list[letter_index])
            out_list[letter_index] = list_of_alphabet[(index_in_alphabet_2 + shift_amount) % length_of_alphabet]
        for chars_index in range(len(out_list)):
            final_hexas.append(mydict_reverse[out_list[chars_index]])
        zzz = ''.join(final_hexas)
        print(zzz)
hurap_file.seek(0)

print("""\n--- bin of encrypted code ---
-----------------------------""", end="\n\n")

for j in hurap_file:
    decimalToHexa = []
    decryptedHexa = []
    final_hexas = []
    final_list = []
    single_hexas = []
    final_binary = []
    if j[0] is '1' or j[0] is '0':
        list2 = split_line_to_binary(str(j.rstrip()))
        for g in list2:
            decimalToHexa.append(decimal_to_hexa(bin_to_decimal(g)))
        decryptedHexa = [a + b for a, b in zip(decimalToHexa[0::2], decimalToHexa[1::2])]
        decryptedHexa = [mydict.get(item) for item in decryptedHexa]
        for index in range(len(decryptedHexa)):
            index_in_alphabet = list_of_alphabet.index(decryptedHexa[index])
            decryptedHexa[index] = list_of_alphabet[(index_in_alphabet - shift_amount) % length_of_alphabet]
        outt = ''.join(decryptedHexa)
        for string in replace_dict.keys():
            if (string in outt) is True:
                outt = outt.replace(string, replace_dict[string])
            elif (string in outt) is False:
                false_count += 1
        if false_count == 7:
            false_count = 0
        elif false_count < 7:
            false_count = 0
        out_list = list(outt)
        for letter_index in range(len(out_list)):
            index_in_alphabet_2 = list_of_alphabet.index(out_list[letter_index])
            out_list[letter_index] = list_of_alphabet[(index_in_alphabet_2 + shift_amount) % length_of_alphabet]
        for chars_index in range(len(out_list)):
            final_hexas.append(mydict_reverse[out_list[chars_index]])
        zzz = ''.join(final_hexas)
        for final_index in range(len(final_hexas)):
            final_list.append(final_hexas[final_index])
        for x in final_hexas:
            single_hexas.append(x[0])
            single_hexas.append(x[1])
        for hexaValues in single_hexas:
            final_binary.append(hexas_to_binary[hexaValues])
            fff = ''.join(final_binary)
        print(fff)
hurap_file.seek(0)
hurap_file.close()
