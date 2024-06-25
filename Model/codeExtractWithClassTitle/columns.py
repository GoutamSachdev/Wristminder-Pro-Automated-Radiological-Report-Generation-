import pandas as pd
import os
from shutil import copyfile
import re

# Load your dataset
dataset_path = r'C:\Users\gksac\OneDrive\Desktop\BinaryReport.csv'  # Update with your dataset path
image_folder = r'C:\Users\gksac\Downloads\Compressed\wrist_abnormality_report_generation_dataset\classes_with_bboxes\\pronatorsign'  # Update with your image folder path
output_folder = r'C:\Users\gksac\Downloads\Compressed\wrist_abnormality_report_generation_dataset\classes_with_bboxes\Match_fff'  # Folder where matched images will be saved

# Create the output folder if it doesn't exist
if not os.path.exists(output_folder):
    os.makedirs(output_folder)

# Load dataset
data = pd.read_csv(dataset_path)

# Assuming your dataset column name containing values is 'column_name', change it if needed
dataset_column = 'filestem'

# Iterate through the dataset and match image names
for index, row in data.iterrows():
    dataset_value = str(row[dataset_column])[:4]  # Extract first four letters from the dataset column value
    
    # Iterate through image files in the image folder
    for image_name in os.listdir(image_folder):
        if image_name.startswith(dataset_value):
            image_path = os.path.join(image_folder, image_name)
            # Copy the image to the output folder
            output_path = os.path.join(output_folder, image_name)
            copyfile(image_path, output_path)
            print(f"Image '{image_name}' copied to '{output_path}'")
            break  # Break the loop after finding the first matching image
    else:
        print(f"No image found for dataset value '{dataset_value}'")

print("Matching process completed.")
