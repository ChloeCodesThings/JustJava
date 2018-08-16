/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    /**
     * This method is called when the order button is clicked.
     */

    public void submitOrder(View view) {
        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();

        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();

        EditText usersName = (EditText) findViewById(R.id.name_field);
        String nameOfUser = usersName.getText().toString();

        int price = calculatePrice(hasWhippedCream, hasChocolate);

        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, nameOfUser);
//        displayMessage(priceMessage);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Yo, coffee order for " + nameOfUser+ "!");
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

    /**
     * Calculates the price of the order.
     *
     * param quantity  is the number of cups of coffee ordered
     */

    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate ) {
        int basePrice = 5;

        if (hasWhippedCream) {
            basePrice = basePrice + 1;
        }

        if (hasChocolate) {
            basePrice = basePrice + 2;
        }

        return quantity * basePrice;

    }

    public void increment(View view) {
        quantity = quantity + 1;
        if (quantity > 100) {
            Toast.makeText(this, "You can't order more than 100 coffees!", Toast.LENGTH_SHORT).show();
            return;
        }
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        quantity = quantity / 2;
        if (quantity < 1) {
            Toast.makeText(this, "You need to order at least 1 coffee...", Toast.LENGTH_SHORT).show();
            return;
        }
        displayQuantity(quantity);
    }

    /**displays message to screen  */

    private void displayMessage (String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }


    /**
     * This method displays the given price on the screen, as well as the user's name, true/false booleans
     * for whipped cream and chocolate, the quantity, and a thank you message
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String nameOfUser) {
        String priceMessage = "\nName: " + nameOfUser;
        priceMessage = priceMessage + "\nAdd whipped cream?: " + addWhippedCream;
        priceMessage = priceMessage + "\nAdd chocolate?: " + addChocolate;
        priceMessage = priceMessage + "\nQuantity: " + quantity;
        priceMessage = priceMessage + "\nTotal: $" + price;
        priceMessage = priceMessage+ "\nThank you!";
        return priceMessage;
    }


}
