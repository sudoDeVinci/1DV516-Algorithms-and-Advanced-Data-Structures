import matplotlib.pyplot as plt
import json
import sys

def plot(graph_path, x, y, x_label, y_label, title) -> None:
    x = json.loads(x)
    y = json.loads(y)
    plt.plot(x, y)

    # Add labels and title
    plt.xlabel(x_label)
    plt.ylabel(y_label)
    plt.title(title)

    # Save the graph
    plt.savefig(graph_path)


def dual_plot(graph_path, x1, y1,x2, y2, x_label, y_label, title) -> None:
    pass


def scatterplot(graph_path, x, y, x_label, y_label, title) -> None:
    pass


if __name__ == "__main__":
    argsl = len(sys.argv)
    graph_path = sys.argv[1]
    x = sys.argv[2]
    y = sys.argv[3]
    x_label = sys.argv[4]
    y_label = sys.argv[5]
    title = sys.argv[6]

    #print(sys.argv)

    plot(graph_path, x, y, x_label, y_label, title)