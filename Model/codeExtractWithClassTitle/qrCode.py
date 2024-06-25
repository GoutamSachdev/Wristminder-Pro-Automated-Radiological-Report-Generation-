# -*- coding: utf-8 -*-
"""
Created on Fri Nov  3 00:43:19 2023

@author: gksac
"""

import qrcode as qr
from PIL import Image

img = qr.make ("")
img.save(".png")


qr=qrcode.QRCODE(version=1,
                 error_correction=qrcode.constants.ERROR_CORRECT_H,
                 box_size=10,border=4,)

qr.add_data("/")
qr.make(fit=True)
img= qr.make_image(fill_color="blue",back_color="black")
img.save('name.png')

