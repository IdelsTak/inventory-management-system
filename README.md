# Inventory Management System
 A JavaFX inventory application system for a small manufacturing organization

## UML class diagram

![uml class diagram](https://github.com/IdelsTak/inventory-management-system/blob/master/UML%20Class%20Diagram.png)

## User interface

### Main form

The Main form contains a Parts pane and a Products pane with TableViews of parts and products; a search field; buttons to add, modify, and delete parts and products; and an Exit button.

![main form](https://github.com/IdelsTak/inventory-management-system/blob/master/screenshots/main-form-screenshot_2022-01-24_17-11-37.png)

### Add part form

Two views of the same form are shown—one for each of the radio buttons.

The user has the option to select In-House or Outsourced to categorize the part.

If the user selects Outsourced, the Company Name field is available.

If the user selects In-House, the Machine ID field is available.

The Inv field stores the number of units of the product that the company currently have available.

The Min field stores the requirement for the minimum number of product items that must be available by the company at any given time, and the Max field denotes the maximum number of product items that the company can store.

The values of the filed Inv, therefore, must be integers between the values stored in the fields Min and Max.

#### In-house

![add part form—in-house](https://github.com/IdelsTak/inventory-management-system/blob/master/screenshots/inhouse-add-part-form-screenshot_2022-01-24_17-12-29.png)

#### Outsourced

![add part form—outsourced](https://github.com/IdelsTak/inventory-management-system/blob/master/screenshots/outsourced-add-part-form-screenshot_2022-01-24_17-13-05.png)

### Modify part form

Two views of the same form are shown—one for each of the radio buttons.

The user has the option to select In-House or Outsourced to categorize the part.

If the user selects Outsourced, the Company Name field is available.

If the user selects In-House, the Machine ID field is available.

The Modify Part form has the same functionality as the Add Part form, but the fields are populated with data that was previously entered and saved.

The ID field is still disabled but now displays the selected part’s ID.

#### In-house

![modify part form—in-house](https://github.com/IdelsTak/inventory-management-system/blob/master/screenshots/inhouse-modify-part-form-screenshot_2022-01-24_17-13-42.png)

#### Outsourced

![modify part form—outsourced](https://github.com/IdelsTak/inventory-management-system/blob/master/screenshots/outsourced-modify-part-form-screenshot_2022-01-24_17-14-08.png)

### Add product form

Product form contains product information, a top TableView containing all the part data to choose from, and a bottom TableView with the associated part data.

![add product form](https://github.com/IdelsTak/inventory-management-system/blob/master/screenshots/add-product-form-screenshot_2022-01-24_17-14-42.png)

### Modify product form

Modify Product form has the same functionality as the Add Product form, but the parts and fields are populated with data that was previously entered and saved.

![modify product form](https://github.com/IdelsTak/inventory-management-system/blob/master/screenshots/modify-product-form-screenshot_2022-01-24_17-15-05.png)
