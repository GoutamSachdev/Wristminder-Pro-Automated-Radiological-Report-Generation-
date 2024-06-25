# -*- coding: utf-8 -*-
"""
Created on Fri Nov 24 01:18:13 2023

@author: gksac
"""

import io
import fitz
from PIL import Image
import pytesseract
from docx import Document
import cv2
import numpy as np

# Set the Tesseract executable path
pytesseract.pytesseract.tesseract_cmd = r'C:\Program Files\Tesseract-OCR\tesseract.exe'

def pdf_to_images(pdf_path):
    pdf_document = fitz.open(pdf_path)
    images = []

    for page_number in range(pdf_document.page_count):
        page = pdf_document[page_number]
        image_list = page.get_images(full=True)

        for img_index, img in enumerate(image_list):
            base_image = pdf_document.extract_image(img[0])
            image_bytes = base_image["image"]

            image = Image.open(io.BytesIO(image_bytes))
            images.append(image)

    return images

def preprocess_image(image):
    # Convert to NumPy array
    np_image = np.array(image)

    # Invert colors (assuming your text is darker than the background)
    inverted_image = cv2.bitwise_not(np_image)

    # Convert to grayscale (if not already in grayscale)
    gray_image = cv2.cvtColor(inverted_image, cv2.COLOR_BGR2GRAY)

    # Adaptive thresholding
    _, im_btw = cv2.threshold(gray_image, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)

    # Dilation
    kernel = np.ones((3, 3), np.uint8)
    image = cv2.dilate(im_btw, kernel, iterations=1)

    # Erosion
    image = cv2.erode(image, kernel, iterations=1)

    # Closing
    image = cv2.morphologyEx(image, cv2.MORPH_CLOSE, kernel)

    # Dilation with a larger kernel
    image = cv2.dilate(image, kernel=np.ones((3, 3), np.uint8), iterations=1)

    # Convert back to PIL Image
    return Image.fromarray(image)

def images_to_text(images):
    text = ''

    for image in images:
        preprocessed_image = preprocess_image(image)
        

       
        
        text += pytesseract.image_to_string(preprocessed_image, lang='eng')

    return text

def text_to_word(text, output_path="C:\\Users\\gksac\\OneDrive\\Desktop\\metal_wed_Oct_4_2023.docx"):
    document = Document()
    document.add_paragraph(text)

    document.save(output_path)
    print(f"Word file saved at: {output_path}")

if __name__ == "__main__":
    pdf_path = r"C:\Users\gksac\OneDrive\Desktop\metal_wed_Oct_4_2023.pdf"
    images = pdf_to_images(pdf_path)
    text = images_to_text(images)
    text_to_word(text)
