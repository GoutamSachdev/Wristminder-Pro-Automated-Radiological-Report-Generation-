import json
import csv
from pathlib import Path

def refine_and_save_csv(target_folder, check_folders, output_folder):
    target_filenames = set()

    # Read the target JSON files in the target folder
    for target_file in Path(target_folder).rglob('*.json'):
        with open(target_file, 'r') as target_json:
            target_data = json.load(target_json)
            target_filenames.update({item['filename'] for item in target_data})

    # Create output folder if it doesn't exist
    Path(output_folder).mkdir(parents=True, exist_ok=True)

    # Iterate through check folders
    for check_folder in check_folders:
        unique_filenames = set()

        # Read the check JSON files in the check folder
        for check_file in Path(check_folder).rglob('*.json'):
            with open(check_file, 'r') as check_json:
                check_data = json.load(check_json)
                check_filenames = {item['filename'] for item in check_data}

                # Refine unique filenames
                unique_filenames.update(check_filenames.intersection(target_filenames))

        # Write unique filenames to a CSV file for each check folder
        check_folder_name = Path(check_folder).name
        output_csv = Path(output_folder) / f"{check_folder_name}_unique_filenames.csv"

        # Ensure a valid filename by removing invalid characters
        output_csv = output_csv.with_name(''.join(char for char in check_folder_name if char.isalnum()) + '_unique_filenames.csv')

        with open(output_csv, 'w', newline='') as csv_file:
            csv_writer = csv.writer(csv_file)
            csv_writer.writerow(['Unique Filenames'])
            print(unique_filenames)
            csv_writer.writerows([[filename] for filename in unique_filenames])

# Example usage
target_folder_path = 'D:\reports\fracture'
check_folders_paths = [
    'D:\reports\boneanomaly',
    'D:\reports\metal',
    'D:\reports\softtissue'
]
output_folder_path = 'D:reports'


refine_and_save_csv(target_folder_path, check_folders_paths, output_folder_path)
