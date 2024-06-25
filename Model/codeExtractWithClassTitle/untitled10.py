# -*- coding: utf-8 -*-
"""
Created on Sat Apr 20 15:26:52 2024

@author: gksac
"""
from skimage import data

image_names = [name for name in dir(data) if not name.startswith("_")]
print(image_names)
