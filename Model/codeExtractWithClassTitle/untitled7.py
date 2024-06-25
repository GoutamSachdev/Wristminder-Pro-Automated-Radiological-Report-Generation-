import requests
from PIL import Image
from io import BytesIO
import os
import pandas as pd
from datasets import load_dataset
import os

df=pd.read_csv("D:\\GIT MULITMODEL\ReportWithImage.csv")
# Remove duplicates

df = df.drop_duplicates()
df.to_json('train.jsonl', orient='records', lines=True)
print(os.getcwd())
train_dataset = load_dataset('json', data_files='D:\\FYP\C7 20F 25 Wristminder Pro(Automated Radiological Report Generation)  Dr Sher Muhmmand Daudpota Suman Bai\reports Work\codeExtractWithClassTitle\train.jsonl', split="train")
imageUrl=[]
# Map the format and populate the data dictionary
for example in train_dataset:
   
    imageUrl.append(f"https://github.com/GoutamSachdev/ReportDataSet/blob/main/Match_fracture/{example['image_name']}?raw=true")
    

# Function to download image from URL and return pixel values
def process_image_from_url(image_url):
    try:
        response = requests.get(image_url)
        if response.status_code == 200:
            image_data = response.content
            image = Image.open(BytesIO(image_data))
            if image.mode != "RGB":
                image = image.convert("RGB")
            return list(image.getdata())
        else:
            print(f"Error downloading image from URL {image_url}: HTTP status code {response.status_code}")
            return None
    except Exception as e:
        print(f"Error processing image from URL {image_url}: {e}")
        return None

# List of image URLs or image names
image_urls = imageUrl
count=0
# Process images from URLs
all_pixel_data = []
for image_url in image_urls:
    pixel_data = process_image_from_url(image_url)
    if pixel_data is not None:
        all_pixel_data.append(pixel_data)
        print(count)
        count=count+1

# `all_pixel_data` now contains the pixel data of all images downloaded from the URLs
print(all_pixel_data)