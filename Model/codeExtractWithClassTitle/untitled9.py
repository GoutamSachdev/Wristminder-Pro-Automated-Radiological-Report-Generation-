import cv2
import numpy as np
from matplotlib import pyplot as plt

def PlotImage(Img, Title):
    plt.imshow(Img, cmap='gray')
    plt.title(Title)
    plt.xticks([])
    plt.yticks([])
    plt.show()

# Loading image
img0 = cv2.imread('C:\\Users\gksac\Downloads\SanFrancisco.jpg')

gblur = cv2.GaussianBlur(img0, (3, 3), 0)

laplacian_kernel1 = np.array([[0, 1, 0], [1, -4, 1], [0, 1, 0]])
laplacian_kernel2 = np.array([[1, 1, 1], [1, -8, 1], [1, 1, 1]])

# Apply Laplacian filters
img1 = cv2.filter2D(gblur, -1, laplacian_kernel1)
img2 = cv2.filter2D(gblur, -1, laplacian_kernel2)

# Combine filtered images using addition
combined_img = cv2.add(img1, img2)
minus_img=cv2.subtract(gblur,combined_img)
# Plotting the images
fig, axes = plt.subplots(nrows=2, ncols=2, figsize=(12, 12))
ax = axes.ravel()

ax[0].imshow(img0[:, :, ::-1])  # Convert BGR to RGB
ax[0].set_title('Original image')

ax[1].imshow(img1, cmap=plt.cm.gray)
ax[1].set_title("Laplacian -4 filter")

ax[2].imshow(img2, cmap=plt.cm.gray)
ax[2].set_title("Laplacian -8 filter")

ax[3].imshow(combined_img, cmap=plt.cm.gray)
ax[3].set_title("Combined (Addition)")
ax[4].imshow(minus_img, cmap=plt.cm.gray)
ax[4].set_title("minus_img (Addition)")

for a in ax:
    a.axis('off')
plt.tight_layout()
plt.show()
