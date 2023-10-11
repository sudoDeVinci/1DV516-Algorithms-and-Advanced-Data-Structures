import numpy as np
from typing import List

def __power_law(x: list[float], y: list[float]) -> tuple[float, float, float]:
    """
    Calculate power-law eqn for a given set of x and y values.
    Return in the form [y-intercept, slope]
    """
    log_x = np.log2(x)
    log_y = np.log2(y)

    slope, intercept, coefficient = __linear_regression(log_x, log_y)

    a = 2**intercept  # 2 raised to the power of intercept
    b = slope


def __linear_regression(x: list[float], y: list[float]) -> tuple[float, float, float]:
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


def __generate_expected_data(slope: float, intercept:float,x: list[float], plot_type:str):
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


def __get_graph_data(x_coords: List[float], y_coords: List[float], type: str) -> tuple(str, float, List[float]):
    if type == "Linear":
        slope, intercept, r_value = __linear_regression(x_coords, y_coords)
        equation = f'Linear Fit (y={slope:.3f}x + {intercept:.3f})'

    elif type == "Exponential":
        slope, intercept, r_value = __power_law(x_coords, y_coords)
        equation = f'Exponential Fit (y={intercept:.3f} * x^{slope:.3f})'

    elif type == "None":
        equation = None
        slope = None
        intercept = None
        r_value = None

    else:
        raise ValueError("Invalid plot_type. Use 'Linear', 'Exponential', or 'None'.")

    # Generate expected values
    expected_data = __generate_expected_data(slope, intercept, x_coords, type)
    
    return (equation, r_value, expected_data)

def line_graph(graph_path:str, x:  List[List[float]], y: List[List[float]], x_label:str, y_label:str, title:str, plot_type: List[str]) -> None:
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
    