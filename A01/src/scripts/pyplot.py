import sys
from numpy import log2
from typing import List
from os import path, makedirs
import matplotlib.pyplot as plt
from matplotlib.axes import Axes
from plotmath import *
from plotters import *


"""
Tuple of colour-marker pairs for graphing.
"""
COLOUR_MARKERS:List[tuple[str,str]] = [
    ('b', 'o'),  # blue, circle
    ('g', '^'),  # green, triangle up
    ('r', 's'),  # red, square
    ('c', 'D'),  # cyan, diamond
    ('m', 'p'),  # magenta, pentagon
    ('y', '*'),  # yellow, star
    ('k', 'x'),  # black, x
    ('#FFA07A', 'H'),  # LightSalmon, hexagon1
    ('#20B2AA', 'v'),  # LightSeaGreen, triangle down
    ('#8A2BE2', '8'),  # BlueViolet, octagon
    ('#7FFF00', 'D'),  # Chartreuse, diamond
    ('#FF4500', 'h'),  # OrangeRed, hexagon2
    ('#9932CC', 'p'),  # DarkOrchid, pentagon
    ('#3CB371', 'X'),  # MediumSeaGreen, x
    ('#40E0D0', 'd'),  # Turquoise, thin_diamond
    ('#FFD700', '+'),  # Gold, plus
    ('#DC143C', '|'),  # Crimson, vline
    ('#800080', '_')   # Purple, hline
]

"""
List of all graph types supported
"""
TYPES: List[str] = ["Linear","Exponential","Histogram","Scatter","None"]


def graph(graph_path:str, x:  List[List[float]], y: List[List[float]], x_label:str, y_label:str, title:str, plot_type: List[str], labels: List[str]) -> None:
    """
    Plot a number of plots on the same graph plane.
    
    Args:
        graph_path (str): Path to save graph to
        x (List[List[float]]): List of x coord lists for each plot
        y (List[List[float]]): List of y coord lists for each plot
        x_label (str): x axis label
        y_label (str): y axis label
        title (str): Graph title
        plot_type (List[str]): Lists of plot types.
    """
    # If the number of coord details doesn't match, exit.
    if not (len(x) == len(y) == len(plot_type)):
        print("length mismatch!")
        print(len(x), len(y), len(plot_type))
        sys.exit(1)
    
     # Declare the plot to write to
    _, ax  = plt.subplots(figsize = (8,6))
    
    count = 1
    for x_coords, y_coords, type, markers, label in zip (x, y, plot_type, COLOUR_MARKERS, labels):
        match type:
            case "Linear":
                line_graph(ax, x_coords, y_coords, type, markers, count, label)
            case "Exponential":
                line_graph(ax, x_coords, y_coords, type, markers, count, label)
            case "Histogram":
                pass
            case "Logarithmic":
                line_graph(ax, x_coords, y_coords, type, markers, count, label)
            case "Scatter":
                scatter(ax, x_coords, y_coords, markers, count, label)
            case "None":
                scatter(ax, x_coords, y_coords, markers, count, label)
            case _:
                pass
                
                
        count += 1

    # Add labels and title
    plt.xlabel(x_label)
    plt.ylabel(y_label)
    plt.title(title)
    plt.legend(loc = "upper left", fontsize = 8)

    # Save the graph
    __resolve_path(graph_path)
    plt.savefig(graph_path)


def scatter(ax: Axes,x_coords: List[float], y_coords: List[float],
             markers: tuple[str, str], count:int, label_str: str) -> None:
    
    l:str = f"{label_str}" if label_str != "None" else f"Plot {str(count).zfill(3)} data"
    ax.scatter(x_coords, y_coords,c = markers[0], alpha = 0.8, marker = markers[1], label = l)



def line_graph(ax: Axes,x_coords: List[float], y_coords: List[float],
                type: str, markers: tuple[str, str], count:int,
                label_str: str) -> None:
    
    equation, expected_data = get_graph_data(x_coords, y_coords, type)
    scatter(ax, x_coords, y_coords, markers, count, label_str)
    if expected_data is not None:
        l:str = f"{label_str} approx. to {equation}" if label_str != "None" else f"Plot {str(count).zfill(3)} data approx. to {equation}"
        ax.plot(x_coords, expected_data, label = l)



def line_graph(ax: Axes,x_coords: List[float], y_coords: List[float],
                type: str, markers: tuple[str, str], count:int,
                label_str: str, analyze: bool) -> None:
    pass


def __resolve_path(image_path:str) -> None:
    """
    Ensure graph save path exists.
    
    Args:
        image_path (str): _description_
    """
    
    basefolder:str = path.dirname(image_path)
    print(basefolder)
    if not path.isdir(basefolder):
        makedirs(basefolder)
    



if __name__ == "__main__":
    
    import argparse
    import ast
    import platform

    system = platform.system()
    print(system)
    
    # Define a custom type function to convert strings to lists
    def parse_list(s):
        return ast.literal_eval(s)


    parser = argparse.ArgumentParser(description='Process some arguments.')
    
    # Define the expected arguments
    parser.add_argument('graph_path', type=str, help='Path to the image file')
    parser.add_argument('x', type=parse_list, help='List of lists of integers')
    parser.add_argument('y', type=parse_list, help='List of lists of integers')
    parser.add_argument('x_label', type=str, help='Label for data1')
    parser.add_argument('y_label', type=str, help='Label for data2')
    parser.add_argument('title', type=str, help='Title of the plot')
    


    if system == "Linux": 
        parser.add_argument('plot_type', type=parse_list, help='List of strings')
        parser.add_argument('labels', type=parse_list, help='List of strings')
    else:
        parser.add_argument('plot_type', type=str, help='List of strings')
        parser.add_argument('labels', type=str, help='List of strings')
    
    parser.add_argument('analyze', type=bool, help='Boolean')

    # Parse
    args = parser.parse_args()
    
    # Assign the args
    argsl = len(sys.argv)
    graph_path: str = args.graph_path
    x: List[List[float]] | List[float] = args.x
    y: List[List[float]] | List[float] = args.y
    x_label: str = args.x_label
    y_label: str = args.y_label
    title: str = args.title
    analyze: bool = args.analyze

    if system == "Linux": 
        plot_type: List[str] = args.plot_type 
        labels: List[str] = args.labels

    if system == "Windows":
        plot_type = sys.argv[7].split(",")
        plot_type =  [l.replace("[",'').replace("]",'').strip() for l in plot_type]
        labels = sys.argv[8].split(",")
        labels = [l.replace("[",'').replace("]",'').strip() for l in labels]
        
    
    print(f"Image Path: {graph_path}")
    print(f"X: {x} - {len(x)}: \n{type(x)} of {type(x[0])} of {type(x[0][0])}")
    print(f"Y: {y} - {len(y)}")
    print(f"X Label : {x_label}")
    print(f"Y Label : {y_label}")
    print(f"Title: {title}")
    print(f"Types: {plot_type} - {len(plot_type)}")
    print(f"Types: {labels} - {len(labels)}")
    print(f"Constructing {len(plot_type)} graphs")
    print(f"Analysis: {analyze}")
    
    graph(graph_path, x, y, x_label, y_label, title, plot_type, labels, analyze)