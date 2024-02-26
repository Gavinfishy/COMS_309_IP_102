import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class TitleComponent extends TextView {

    public TitleComponent(Context context) {
        super(context);
        init();
    }

    public TitleComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // Customize the appearance of your title component
        setTextSize(24); // Set the text size to your preference
        setTextColor(Color.BLACK); // Set the text color
        setTypeface(Typeface.DEFAULT_BOLD); // Set the text style to bold
    }
}
