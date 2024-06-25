import json
import os
import shutil

# Assuming you have a function to process each JSON file
def process_json_file(file_path, output_directory):
    with open(file_path, "r") as json_file:
        try:
            json_data = json.load(json_file)

            # Check if "classTitle" is "soft tissue"
            if has_soft_tissue(json_data):
                # Save the JSON file to another directory
                output_path = os.path.join(output_directory, os.path.basename(file_path))
                shutil.copyfile(file_path, output_path)
                print(f"Saved {file_path} to {output_directory}")

        except json.JSONDecodeError as e:
            print(f"Error decoding JSON in file {file_path}: {e}")

# Function to check if "classTitle" is "soft tissue"
def has_soft_tissue(json_data):
    objects = json_data.get("objects", [])
    for obj in objects:
        if obj.get("classTitle") == "text":
            return True
    return False

# Directory containing your JSON files
json_files_directory = "C:\\Users\\gksac\\Downloads\\Compressed\\supervisely\\wrist\\ann"


# Directory to save JSON files with "soft tissue"
output_directory =r"D:\\reports\\text"

# Ensure the output directory exists, create if necessary
os.makedirs(output_directory, exist_ok=True)

# Iterate over each JSON file in the directory
for filename in os.listdir(json_files_directory):
    if filename.endswith(".json"):
        file_path = os.path.join(json_files_directory, filename)

        # Process each JSON file
        process_json_file(file_path, output_directory)
