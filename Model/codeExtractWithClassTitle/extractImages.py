import re
import os
import shutil


def copy_images_matching_json(json_folder, image_folder, output_folder):
    os.makedirs(output_folder, exist_ok=True)

    # Get a list of JSON file names
    json_files = [file for file in os.listdir(json_folder) if file.endswith(".png")]
    

    for json_filename in json_files:
    # Extract the base filename (excluding extension) from the JSON filename
        json_base_filename = os.path.splitext(json_filename)[0]
        json_first_four_chars = json_base_filename[:11]
       
        

        # List image files in the image folder
        matching_image_files = [image_filename for image_filename in os.listdir(image_folder_path) if image_filename.startswith(json_first_four_chars)]

        for image_filename in matching_image_files:
            # Construct the full paths
            image_path = os.path.join(image_folder_path, image_filename)
            output_path = os.path.join(output_folder, image_filename)

            # Copy the image file to the output folder
            shutil.copy(image_path, output_path)
            print(f"Copied {image_filename} to {output_folder}")








json_folder_path = r"C:\Users\gksac\Downloads\Compressed\wrist_abnormality_report_generation_dataset\classes_with_bboxes\softtissue"
image_folder_path = r'D:\Wrist -XRay-datasets-img\image 1'


output_folder_path = r"D:\reports\softtissue-aImages"

copy_images_matching_json(json_folder_path, image_folder_path, output_folder_path)
