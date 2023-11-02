from numpy import log2
# from scipy.stats import linregress



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
    r_value = r_numer/r_denom if r_denom>0 else 0
    
    
    return slope, intercept, r_value


def power_law(x: list[float], y: list[float]) -> tuple[float, float, float]:
    """
    Calculate power-law eqn for a given set of x and y values.
    Return in the form [y-intercept, slope]
    """
    log_x = log2(x)
    log_y = log2(y)

    slope, intercept, coefficient = linear_regression(log_x, log_y)

    a = 2**intercept  # 2 raised to the power of intercept
    b = slope
    
    return b, a, coefficient


def generate_expected_data(slope: float, intercept:float,x: list[float], plot_type:str):
    """
    Generate expected values for a given list of values for an indepedent variable
    accordintg the the type of plot.
    """

    match plot_type:
        case "Linear":
            return [(slope*x_val)+intercept for x_val in x]
        case "Exponential":
            return [intercept*(x_val**slope) for x_val in x]
        case "Logarithmic":
            return [intercept*log2(x_val*slope) for x_val in x]
        case _:
            return None
