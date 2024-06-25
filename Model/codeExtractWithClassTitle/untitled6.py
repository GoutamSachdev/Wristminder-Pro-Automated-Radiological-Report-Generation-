import pandas as pd

# Load datasets
dataset1 = pd.read_csv('C:\\Users\gksac\OneDrive\Desktop\dataset.csv', encoding='latin1')  # Assuming dataset1 contains 'patient_id' and 'filestem' columns
dataset2 = pd.read_csv('C:\\Users\gksac\OneDrive\Desktop\Wrist_Reports.csv', encoding='latin1')  # Assuming dataset2 contains 'patient_id' and other columns

# Drop duplicates in dataset1 and keep only the first occurrence of each patient ID
first_filestem = dataset1.drop_duplicates(subset='patient_id', keep='first')

# Merge dataset2 with the first_filestem based on 'patient_id', filling missing values in 'filestem' from dataset2
matched_dataset = pd.merge(dataset2, first_filestem[['patient_id', 'filestem']], on='patient_id', how='left')

# Save the matched dataset to a new CSV file
matched_dataset.to_csv('matched_dataset1.csv', index=False)

print("Matched dataset saved successfully!")
