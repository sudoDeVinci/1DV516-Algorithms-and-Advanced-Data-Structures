from matplotlib.axes import Axes
from typing import List
from plotmath import *




def get_graph_data(x_coords: List[float], y_coords: List[float], type: str) -> tuple[str, List[float]]:
    
    match type:
        case "Linear":
            slope, intercept, r_value = linear_regression(x_coords, y_coords)
            equation = fr'$y={slope:.6f} \cdot x + {intercept:.6f}$' + '\n' + f'r: {r_value:.3f}'

        case "Exponential":
            slope, intercept, r_value = power_law(x_coords, y_coords)
            equation = fr'$y={intercept:.6f} \cdot x^{{ {slope:.6f} }}$' + '\n' + f'r: {r_value:.3f}'

        case "Logarithmic":
            slope, intercept, r_value = power_law(x_coords,y_coords)
            equation = fr'$y={intercept:.6f} \cdot \log_2({slope:.6f} \cdot x)$' + '\n' + f'r: {r_value}'

        case _:
            equation = None
            slope = None
            intercept = None

    # Generate expected values
    expected_data = generate_expected_data(slope, intercept, x_coords, type)
    
    return (equation, expected_data)




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
    
    line_graph(ax, x_coords, y_coords, type, markers, count, label_str, analyze)
    