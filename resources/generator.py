import sys
import os
import random


def main():
    arg1 = 0
    arg2 = 0
    if "--help" in sys.argv:
        print("USE: python3 generator.py" +
              " <<number_of_instructions>> <<number_of_files>> " +
              "<<folder_name>>")
        return
    if len(sys.argv) != 4:
        print("ERROR: NEED 3 ARGUMENTS")
    try:
        arg1 = int(sys.argv[1])
        arg2 = int(sys.argv[2])
    except ValueError:
        print("ERROR: PASS 2 INTEGER IN THE 2 FIRST ARGUMENTS")

    os.mkdir(sys.argv[3])
    for i in range(0, arg2):
        file_path = os.path.join(sys.argv[3], f"program_test_{i+1}.txt")
        file = open(file_path, "w+")
        file.write(f"program program_test_{i+1}.txt\n")
        file.write("begin\n")
        for j in range(0, arg1):
            instruction_type = ""
            if random.randint(0, 100) > 30:
                instruction_type = "execute\n"

            else:
                instruction_type = "block"
                block_time = random.randint(1, 5)
                instruction_type += (' ' + str(block_time) + '\n')

            file.write(instruction_type)
        file.write("end\n")

        file.close()


if __name__ == "__main__":
    main()
