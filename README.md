# Inventory Management System
 A JavaFX inventory application system for a small manufacturing organization

## User interface

### Main form

The Main form contains a Parts pane and a Products pane with TableViews of parts and products; a search field; buttons to add,
modify, and delete parts and products; and an Exit button.

![main form](https://github.com/IdelsTak/inventory-management-system/blob/master/screenshots/add-product-form-screenshot_2022-01-24_17-14-42.png)

### Add part form

Two views of the same form are shown—one for each of the radio buttons.

The user has the option to select In-House or Outsourced to categorize the part.

If the user selects Outsourced, the Company Name field is available.

If the user selects In-House, the Machine ID field is available.

The Inv field stores the number of units of the product that the company currently have available.

The Min field stores the requirement for the minimum number of product items that must be available by the company at any given time, and the Max field denotes the maximum number of product items that the company can store.

The values of the filed Inv, therefore, must be integers between the values stored in the fields Min and Max.

#### In-house

![add part form - in-house](https://github.com/IdelsTak/inventory-management-system/blob/master/screenshots/inhouse-add-part-form-screenshot_2022-01-24_17-12-29.png)

#### Outsourced

![add part form - outsourced](https://github.com/IdelsTak/inventory-management-system/blob/master/screenshots/outsourced-add-part-form-screenshot_2022-01-24_17-13-05.png)

