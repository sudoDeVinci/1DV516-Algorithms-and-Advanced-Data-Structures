from sys import argv


def main(arg1, arg2):
    # Do something with arg1 and arg2
    print(arg1)
    print(arg2)
    print(arg3)

if __name__ == "__main__":
    arg1 = argv[1]
    arg2 = argv[2]
    arg3 = argv[3]
    main(arg1, arg2, arg3)