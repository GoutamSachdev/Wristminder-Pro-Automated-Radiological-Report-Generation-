# -*- coding: utf-8 -*-
"""
Created on Sat Mar 23 15:31:52 2024

@author: gksac
"""

import pandas as pd

def match_patient_ids(dataset1, dataset2):
    # Merge datasets on 'patient_id' column to match patient IDs
    merged_dataset = pd.merge(dataset1, dataset2, on='patient_id', how='inner')
    
    # Identify rows where 'gender' differs between the two datasets
    different_gender = merged_dataset['gender_x'] != merged_dataset['gender_y']
    
    # Create a new column 'gender' containing gender from dataset1 if it differs from dataset2
    merged_dataset.loc[different_gender, 'gender'] = merged_dataset['gender_x']
    
    # Drop columns 'gender_x' and 'gender_y' as they are no longer needed
    merged_dataset.drop(['gender_x', 'gender_y'], axis=1, inplace=True)
    
    # Group by 'patient_id' and select the first occurrence of 'flemstem'
    matched_dataset = merged_dataset.groupby('patient_id').agg({'filestem': 'first', 'gender': 'first'}).reset_index()
    
    return matched_dataset

# Load datasets
dataset1 = pd.read_csv('C:\\Users\gksac\OneDrive\Desktop\dataset.csv',encoding='latin1')  # Assuming dataset1 contains 'patient_id' and 'flemstem' columns
dataset2 = pd.read_csv('C:\\Users\gksac\OneDrive\Desktop\Wrist_Reports.csv',encoding='latin1')  # Assuming dataset2 contains 'patient_id' column

# Call the function to match patient IDs and extract 'flemstem' column
matched_dataset = match_patient_ids(dataset1, dataset2)

# Save the matched dataset to a new CSV file
matched_dataset.to_csv('matched_dataset.csv', index=False)

print("Matched dataset saved successfully!")
