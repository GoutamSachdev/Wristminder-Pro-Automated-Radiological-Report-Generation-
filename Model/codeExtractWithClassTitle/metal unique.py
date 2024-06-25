incremented_number =0
while True:
    try:
        # Get input from the user
       
        user_input = input("Enter a number or 'finished' to exit: ")
        
        # Check if the user wants to exit
        if user_input.lower() == 'finished':
            print("Exiting the program.")
            break  # Exit the loop if 'finished' is entered

        # Convert input to float and increment
        number = float(user_input)
        incremented_number = incremented_number + number

        # Display the result
        print(f"The incremented value is: {incremented_number}")

    except ValueError:
        print("Error: Please enter a valid number or 'finished'.")
        continue  # Continue to the next iteration of the loop even if there's an error
