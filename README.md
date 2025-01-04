# Calculator App

This project is an Android-based calculator app that performs basic arithmetic and power calculations. The app features a clean user interface and supports expressions with parentheses, decimal values, and exponents. Below is a detailed description of the app and instructions for building and running it.

## Features

1. **Basic Arithmetic Operations**:
   - Addition (`+`)
   - Subtraction (`-`)
   - Multiplication (`*`)
   - Division (`/`)

2. **Exponentiation**:
   - The `^` operator is used to calculate powers. It is converted internally to `Math.pow()` for accurate results.

3. **Parentheses Handling**:
   - Users can add parentheses `(` and `)` for complex expressions.
   - Bracket balancing is enforced to avoid invalid inputs.

4. **Backspace Functionality**:
   - Users can remove the last entered character using the backspace button.

5. **Clear Button**:
   - Resets the entire input and result display.

6. **Scientific Notation Support**:
   - Results are displayed in scientific notation (`E` format) if necessary.

7. **Error Handling**:
   - Detects and handles invalid expressions (e.g., unmatched parentheses, invalid syntax).

8. **Formatted Results**:
   - Results are formatted to display up to 8 significant digits for precision and clarity.

## User Interface

- A screenshot of the calculator app's interface is included in the repository.
- The UI consists of the following components:
  - **Workings TextView**: Displays the user-entered expression.
  - **Results TextView**: Displays the calculated result.
  - **Buttons**: Number keys (0-9), operators, parentheses, clear, backspace, and equals.

## Video


https://github.com/user-attachments/assets/b28cd0c3-3e63-4688-befe-b6a365d02725


## Code Overview

The app is built using **Java** for Android. Below is an overview of the key files:

### `MainActivity.java`
- Handles the logic for the calculator's functionality.
- Includes methods to handle button clicks and update the display.
- Uses the `ScriptEngine` from Java's `javax.script` package to evaluate mathematical expressions.

#### Key Methods
1. `setWorkings(String givenValue)`:
   - Updates the expression displayed in the workings TextView.

2. `equalOnClick(View view)`:
   - Evaluates the current expression and displays the result.
   - Converts the `^` operator to `Math.pow()` for power calculations.

3. `checkForPowerOf()`:
   - Processes the input expression to identify and replace the `^` operator with `Math.pow()` syntax.

4. `changeFormula(Integer index)`:
   - Handles parentheses and numeric extraction for the `^` operator.
   - Ensures proper syntax for power calculations.

5. `findClosingParenthesis(int openIndex)` and `findOpeningParenthesis(int closeIndex)`:
   - Identify matching parentheses for complex expressions.

6. `backSpaceOnClick(View view)`:
   - Removes the last character from the input expression.

### `activity_main.xml`
- Defines the layout of the calculator app.
- Includes buttons for digits, operators, and other controls.

## Build and Run Instructions

1. **Prerequisites**:
   - Android Studio installed on your computer.
   - Java Development Kit (JDK) configured.

2. **Clone the Repository**:
   ```bash
   git clone <repository_url>
   cd <repository_folder>
   ```

3. **Open in Android Studio**:
   - Open Android Studio.
   - Select "Open an Existing Project" and navigate to the cloned repository.

4. **Build the Project**:
   - Click on "Build" > "Make Project" or press `Ctrl+F9`.

5. **Run the App**:
   - Connect an Android device or start an emulator.
   - Click the "Run" button or press `Shift+F10`.

## Usage

- Enter a mathematical expression using the buttons.
- Use `=` to calculate the result.
- Use `C` to clear all input, or the backspace button to remove the last character.

### Example Expressions
- `7 + 3 * (10 / (12 / (3 + 1) - 1))`
- `2^3`
- `5 + (6 * 7) - 8 / 4`

## Screenshots
<img src="https://github.com/user-attachments/assets/c48acc08-b12a-442e-8e52-8529d904c70b" width="200" height="445">
 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
<img src="https://github.com/user-attachments/assets/937a44c7-d8c3-4326-a549-3ec3bbce20f2" width="200" height="445">



## Limitations

- Currently, the app supports only basic arithmetic and power operations.
- Advanced mathematical functions (e.g., trigonometry, logarithms) are not included.

## Future Enhancements

1. Add support for additional operators and functions (e.g., `sin`, `cos`, `log`).
2. Provide an option to switch between `E` and `10^` notations.
3. Improve error messages for invalid input.
4. Enhance UI for better usability on different screen sizes.

## License

This project is licensed under the MIT License - see the (https://en.wikipedia.org/wiki/MIT_License#:~:text=The%20MIT%20License%20is%20a,therefore%20has%20high%20license%20compatibility.) for details.

---

Feel free to suggest improvements or report issues by creating a new issue in the repository!

