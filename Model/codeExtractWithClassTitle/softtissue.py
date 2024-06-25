

import os
import shutil
    

def copy_images_matching_json(json_folder, image_folder, output_folder):
    os.makedirs(output_folder, exist_ok=True)

    # Get a list of JSON file names
    json_files = [file for file in os.listdir(json_folder) if file.endswith(".json")]

    for json_filename in json_files:
        # Extract the base filename (excluding extension) from the JSON filename
        json_base_filename = os.path.splitext(json_filename)[0]

        # Construct the corresponding image filename
        image_filename = f"{json_base_filename}.png"
        image_path = os.path.join(image_folder, image_filename)
        output_path = os.path.join(output_folder, image_filename)

        # Check if the image file exists and copy it to the output folder
        
        if os.path.exists(image_path):
            shutil.copy(image_path, output_path)
            print(f"Copied {image_filename} to {output_folder}")
        else:
            print(f"Image not found for {json_filename}")
        
        
        
            
        

       
            

# Example usage
json_folder_path = r"D:\reports\softtissue"
image_folder_path = r'D:\Wrist -XRay-datasets-img\image 3'


output_folder_path = r"D:\reports\periostealreaction-images"

copy_images_matching_json(json_folder_path, image_folder_path, output_folder_path)
