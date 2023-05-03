expression_str_original = input()
expression_str_edit = ""
activated = False
digit_started = False
for char in expression_str_original:
    if not char.isdigit():
        digit_started = False
        if char == "-":
            if activated:
                expression_str_edit += ")-("
                activated = True
                continue
            else:
                expression_str_edit += "-("
                activated = True
                continue
    else:
        if char == "0" and (not digit_started):
            continue
        else:
            digit_started = True
    expression_str_edit += char
if activated:
    expression_str_edit += ")"

print(eval(expression_str_edit))
