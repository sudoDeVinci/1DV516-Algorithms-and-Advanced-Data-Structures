import matplotlib.pyplot as plt
import json
import sys
import numpy as np
from typing import List

COLOUR_MARKERS:List[tuple[str,str]] = [('b','x'),('r','o'),('g','*'),('c','P'),('y','^')]


def scatterplot(graph_path, x, y, x_label, y_label, title) -> None:
    pass


def multi_line(graph_path:str, x:  List[List[float]], y: List[List[float]], x_label:str, y_label:str, title:str, plot_type: List[str]) -> None:
    """
    Plot a number of plots on the same graph plane.
    
    Args:
        graph_path (str): Path to save graph to
        x (List[List[float]]): List of x coord lists for each plot
        y (List[List[float]]): List of y coord lists for each plot
        x_label (str): x axis label
        y_label (str): y axis label
        title (str): Graph title
        plot_type (List[str]): Type of plot: 'Linear' or 'Exponential'.
    """
    
    # If the number of coord details doesn't match, exit.
    if not (len(x) == len(y) == len(plot_type)):
        sys.exit(1)
    
    # Declare the plot to write to
    _, ax  = plt.subplots(figsize = (8,6))
    
    count = 1
    
    for x_coords, y_coords, type, markers in zip (x, y, plot_type, COLOUR_MARKERS):
        # Get the graph type
        if type == "Linear":
            slope, intercept, r_value = linear_regression(x_coords, y_coords)
            equation = f'Linear Fit (y={slope:.3f}x + {intercept:.3f})'

        elif type == "Exponential":
            slope, intercept, r_value = power_law(x_coords, y_coords)
            equation = f'Exponential Fit (y={intercept:.3f} * x^{slope:.3f})'

        elif type == "None":
            equation = None
            slope = None
            intercept = None
            r_value = None

        else:
            raise ValueError("Invalid plot_type. Use 'Linear', 'Exponential', or 'None'.")

        # Generate expected values
        expected_data = generate_expected_data(slope, intercept, x_coords, type)

        print(equation)
        ax.scatter(x_coords, y_coords,c = markers[0], alpha = 0.8, marker = markers[1], label = f"Plot {str(count).zfill(3)} measured data")
        if expected_data is not None:
            ax.plot(x_coords, expected_data, label = f"{equation} \nCorrelation coef: {r_value:.3f}")
            # plt.text(0.05, 1.6+(0.01*count-1), f'Plot {str(count).zfill(3)} Correlation coef. : {r_value:.3f}', transform=plt.gca().transAxes, fontsize=10)
        
        count+=1
    
    # Add labels and title
    plt.xlabel(x_label)
    plt.ylabel(y_label)
    plt.title(title)
    plt.legend(loc = "upper left")

    # Save the graph
    plt.savefig(graph_path)
        

def single_line(graph_path:str, x: List[float], y: List[float], x_label:str, y_label:str, title:str, plot_type: str) -> None:
    """
    Simple plot for list of x and y values.
    """
    

    # Get the graph type
    if plot_type == "Linear":
        slope, intercept, r_value = linear_regression(x, y)
        equation = f'Linear Fit (y={slope:.3f}x + {intercept:.3f})'

    elif plot_type == "Exponential":
        slope, intercept, r_value = power_law(x, y)
        equation = f'Exponential Fit (y={intercept:.3f} * x^{slope:.3f})'

    elif plot_type == "None":
        equation = None
        slope = None
        intercept = None
        r_value = None

    else:
        raise ValueError("Invalid plot_type. Use 'Linear', 'Exponential', or 'None'.")

    # Generate expected values
    expected_data = generate_expected_data(slope, intercept, x, plot_type)

    print(equation)

    plt.figure(figsize=(8, 6))
    plt.scatter(x, y, label='Actual Data')
    if expected_data is not None:
        plt.plot(x, expected_data, label=equation)
        plt.text(0.05, 0.85, f'Correlation coef. : {r_value:.3f}', transform=plt.gca().transAxes, fontsize=12)

    # Add labels and title
    plt.xlabel(x_label)
    plt.ylabel(y_label)
    plt.title(title)
    plt.legend()

    # Save the graph
    plt.savefig(graph_path)


def generate_expected_data(slope: float, intercept:float,x: list[float], plot_type:str):
    """
    Generate expected values for a given list of values for an indepedent variable
    accordintg the the type of plot, either linear or exponential.
    """
    if plot_type == "Linear":
        return [(slope*x_val)+intercept for x_val in x]
    elif plot_type == "Exponential":
        return [intercept*(x_val**slope) for x_val in x]
    else:
        return None


def linear_regression(x: list[float], y: list[float]) -> tuple[float, float, float]:
    """
    Calculate and return the slope, intercept and linear regression coefficient
    from the x and y values passed in.

    slope, intercept, r_value, _, _ = linregress(x, y)
    """
    n = len(x)
    
    x_bar = sum(x)/n
    y_bar = sum(y)/n

    slope = sum((xi - x_bar) * (yi - y_bar) for xi, yi in zip(x,y)) / sum((xi - x_bar)**2 for xi in x)

    intercept = y_bar - slope*x_bar


    sum_xy = sum(xi*yi for xi, yi in zip(x,y))
    sum_x = sum(x)
    sum_y = sum(y)
    sum_x_sq = sum(xi**2 for xi in x)
    sum_y_sq = sum(yi**2 for yi in y)

    r_numer = n*sum_xy - sum_x*sum_y
    r_denom = ((n*sum_x_sq - sum_x**2) * (n*sum_y_sq - sum_y**2))**0.5
    r_value = r_numer/r_denom
    
    
    return slope, intercept, r_value

def power_law(x: list[float], y: list[float]) -> tuple[float, float, float]:
    """
    Calculate power-law eqn for a given set of x and y values.
    Return in the form [y-intercept, slope]
    """
    log_x = np.log2(x)
    log_y = np.log2(y)

    slope, intercept, coefficient = linear_regression(log_x, log_y)

    a = 2**intercept  # 2 raised to the power of intercept
    b = slope

    return b, a, coefficient

def copiable_printout():
    print()


if __name__ == "__main__":
    
    import argparse
    import ast
    
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
    parser.add_argument('plot_type', type=parse_list, help='List of strings')

    # Parse
    args = parser.parse_args()

    # Assign the args
    argsl = len(sys.argv)
    graph_path: str = args.graph_path
    x: List[List[float]] | List[float] = args.x
    y: List[List[float]] | List[float] = args.y
    x_label = args.x_label
    y_label = args.y_label
    title = args.title
    plot_type: List[str] | str = args.plot_type 
    
    print(f"Image Path: {graph_path}")
    print(f"X: {x}")
    print(f"Y: {y}")
    print(f"X Label : {x_label}")
    print(f"Y Label : {y_label}")
    print(f"Title: {title}")
    print(f"Types: {plot_type}")

    print("________________________________________________")
    print(f"Graph types: {plot_type}")
    
    if "Scatter" in plot_type:
        scatterplot(graph_path, x, y, x_label, y_label, title, plot_type)
    
    if(len(plot_type) > 1):
        multi_line(graph_path, x, y, x_label, y_label, title, plot_type)
    else:
        single_line(graph_path, x, y, x_label, y_label, title, plot_type)
    

    """
    # print(f"{sys.argv} \n")
    print("\n________________________________________________")
    print(f"Creating plot for {x_label} versus {y_label}")
    print("________________________________________________")
    for xv, yv in zip(x, y):
        print(f"X: {xv:.3f}\tY: {yv:.3f}")
    print("________________________________________________")
    print(f"Graph type: {plot_type}")
    plot(graph_path, x, y, x_label, y_label, title, plot_type)
    print("________________________________________________")
    """
    