import matplotlib.pyplot as plt
import json
import sys

def plot(graph_path, x, y, x_label, y_label):
    x = json.loads(x)
    y = json.loads(y)
    plt.plot(x, y)
    # Add labels and title
    plt.xlabel(x_label)
    plt.ylabel(y_label)
    plt.title('Line Graph')

    # Show the graph
    plt.savefig(graph_path)

if __name__ == "__main__":
    argsl = len(sys.argv)
    # Check for correct args
    if not (argsl == 4 or argsl == 6):
        exit()
    
    x_label = "Elements"
    y_label = "Time"
    graph_path = sys.argv[1]
    x = sys.argv[2]
    y = sys.argv[3]

    if argsl == 6:
        x_label = sys.argv[4]
        y_label = sys.argv[5]


    plot(graph_path, x, y, x_label, y_label)