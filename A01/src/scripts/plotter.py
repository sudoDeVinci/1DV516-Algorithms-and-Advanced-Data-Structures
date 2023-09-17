import matplotlib.pyplot as plt
import json
import sys
import numpy as np
from scipy.stats import linregress




def plot(graph_path:str, x:list[float], y:list[float], x_label:str, y_label:str, title:str, plot_type: str) -> None:
    """
    Simple plot for list of x and y values.
    """

    # Get the 
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
    """
    slope, intercept, r_value, _, _ = linregress(x, y)
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


def dual_plot(graph_path, x1, y1,x2, y2, x_label, y_label, title) -> None:
    pass


def scatterplot(graph_path, x, y, x_label, y_label, title) -> None:
    pass

def copiable_printout():
    print()


if __name__ == "__main__":

    argsl = len(sys.argv)
    graph_path = sys.argv[1]
    x = sys.argv[2]
    y = sys.argv[3]
    x_label = sys.argv[4]
    y_label = sys.argv[5]
    title = sys.argv[6]
    plot_type = sys.argv[7] 

    # Convert data to float arrays.
    x = json.loads(x)
    y = json.loads(y)

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