package com.iprint.app.widget;

/*
 * The content of this file is (c) 2003 - 2011 dmc digital media center GmbH. All rights reserved.
 * 
 * This software is the confidential and proprietary information of dmc digital media center GmbH.
 */
import com.iprint.app.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

/**
 * Custom info dialog.
 * 
 * @author horlaste
 */
public class InfoDialog extends Dialog
{
	/**
	 * Constructs a new info dialog.
	 * 
	 * @param context
	 *            The context.
	 * @param theme
	 *            The theme id.
	 */
	public InfoDialog(Context context, int theme)
	{
		super(context, theme);
	}

	/**
	 * Constructs a new info dialog.
	 * 
	 * @param context
	 *            The context.
	 */
	public InfoDialog(Context context)
	{
		super(context);
	}

	@Override
	public void setCancelable(boolean cancel)
	{
		super.setCancelable(cancel);
	}

	/**
	 * Helper class for creating a custom dialog.
	 */
	public static class InfoDialogBuilder
	{
		private Context context;
		private String title;
		private String message;
		private String positiveButtonText;
		private String negativeButtonText;
		private String helpButtonText;
		private String errorText;
		private Bitmap preview;
		private boolean cancelable = true;
		private DialogInterface.OnClickListener positiveButtonClickListener, negativeButtonClickListener, helpButtonClickListener;

		/**
		 * Constructs a new info dialog builder.
		 * 
		 * @param context
		 *            The context.
		 */
		public InfoDialogBuilder(Context context)
		{
			this.context = context;
		}

		/**
		 * Set the Dialog message from String.
		 * 
		 * @param message
		 *            The message.
		 * 
		 * @return The info dialog builder.
		 */
		public InfoDialogBuilder setMessage(String message)
		{
			this.message = message;
			return this;
		}

		/**
		 * Set the Dialog message from resource.
		 * 
		 * @param message
		 *            The message resource id.
		 * 
		 * @return The info dialog builder.
		 */
		public InfoDialogBuilder setMessage(int message)
		{
			this.message = (String) context.getText(message);
			return this;
		}

		/**
		 * Set the Dialog title from resource.
		 * 
		 * @param title
		 *            The title resource id.
		 * 
		 * @return The info dialog builder.
		 */
		public InfoDialogBuilder setTitle(int title)
		{
			this.title = (String) context.getText(title);
			return this;
		}

		/**
		 * Set the Dialog title from String.
		 * 
		 * @param title
		 *            The title.
		 * 
		 * @return The info dialog builder.
		 */
		public InfoDialogBuilder setTitle(String title)
		{
			this.title = title;
			return this;
		}

		public InfoDialogBuilder setCancelable(boolean bool)
		{
			cancelable = bool;
			return this;
		}

		/**
		 * Set the positive button resource and it's listener.
		 * 
		 * @param positiveButtonText
		 *            The positive button resource id text.
		 * @param listener
		 *            The on click listener.
		 * 
		 * @return The info dialog builder.
		 */
		public InfoDialogBuilder setPositiveButton(int positiveButtonText, DialogInterface.OnClickListener listener)
		{
			this.positiveButtonText = (String) context.getText(positiveButtonText);
			this.positiveButtonClickListener = listener;
			return this;
		}

		/**
		 * Set the positive button text and it's listener.
		 * 
		 * @param positiveButtonText
		 *            The positive button text.
		 * @param listener
		 *            The on click listener.
		 * 
		 * @return The info dialog builder.
		 */
		public InfoDialogBuilder setPositiveButton(String positiveButtonText, DialogInterface.OnClickListener listener)
		{
			this.positiveButtonText = positiveButtonText;
			this.positiveButtonClickListener = listener;
			return this;
		}

		/**
		 * Set the negative button resource and it's listener.
		 * 
		 * @param negativeButtonText
		 *            The negative button text resource id.
		 * @param listener
		 *            The on click listener.
		 * 
		 * @return The info dialog builder.
		 */
		public InfoDialogBuilder setNegativeButton(int negativeButtonText, DialogInterface.OnClickListener listener)
		{
			this.negativeButtonText = (String) context.getText(negativeButtonText);
			this.negativeButtonClickListener = listener;
			return this;
		}
		
		public InfoDialogBuilder setHelpButton(int helpButtonText, DialogInterface.OnClickListener listener)
		{
			this.helpButtonText = (String) context.getText(helpButtonText);
			this.helpButtonClickListener = listener;
			return this;
		}

		public InfoDialogBuilder setErrorText(String text)
		{
			this.errorText = text;
			return this;
		}
		
		public InfoDialogBuilder setPreviewImage(Bitmap bit)
		{
			this.preview = bit;
			return this;
		}
		
		/**
		 * Set the negative button text and it's listener.
		 * 
		 * @param negativeButtonText
		 *            The negative button text.
		 * @param listener
		 *            The on click listener.
		 * 
		 * @return The info dialog builder.
		 */
		public InfoDialogBuilder setNegativeButton(String negativeButtonText, DialogInterface.OnClickListener listener)
		{
			this.negativeButtonText = negativeButtonText;
			this.negativeButtonClickListener = listener;
			return this;
		}
		
		public InfoDialogBuilder setHelpButton(String helpButtonText, DialogInterface.OnClickListener listener)
		{
			this.helpButtonText = helpButtonText;
			this.helpButtonClickListener = listener;
			return this;
		}
		
		/**
		 * Create the custom dialog.
		 * 
		 * @return The info dialog.
		 */
		public InfoDialog create()
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// instantiate the dialog with the custom Theme
			final InfoDialog dialog = new InfoDialog(context, R.style.Dialog);
			View layout = inflater.inflate(R.layout.info_dialog, null);
			dialog.addContentView(layout, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			dialog.setCancelable(cancelable);
			TextView titleTV = ((TextView) layout.findViewById(R.id.info_dialog_title));
			TextView messageTV = ((TextView) layout.findViewById(R.id.info_dialog_text));
			ImageView previewIV = (ImageView) layout.findViewById(R.id.previewIV);
			TextView errorTextTV = (TextView)layout.findViewById(R.id.errorTV);
			Button positiveButton = ((Button) layout.findViewById(R.id.info_dialog_positive_button));
			Button negativeButton = ((Button) layout.findViewById(R.id.info_dialog_negative_button));
			Button helpButton = ((Button)layout.findViewById(R.id.info_dialog_help_button));
			// set the dialog title
			titleTV.setText(title);
			// set the confirm button
			if (positiveButtonText != null)
			{
				positiveButton.setText(positiveButtonText);
				if (positiveButtonClickListener != null)
				{
					positiveButton.setOnClickListener(new View.OnClickListener()
					{
						@Override
						public void onClick(View v)
						{
							positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
						}
					});
				}
			}
			else
			{
				// if no confirm button just set the visibility to GONE
				positiveButton.setVisibility(View.GONE);
			}
			// set the cancel button
			if (negativeButtonText != null && !negativeButtonText.equals(""))
			{
				negativeButton.setText(negativeButtonText);
				if (negativeButtonClickListener != null)
				{
					negativeButton.setOnClickListener(new View.OnClickListener()
					{
						@Override
						public void onClick(View v)
						{
							negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
						}
					});
				}
			}
			else
			{
				// if no confirm button just set the visibility to GONE
				negativeButton.setVisibility(View.GONE);
			}
			if(helpButtonText != null && !helpButtonText.equals(""))
			{
				helpButton.setText(helpButtonText);
				helpButton.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						helpButtonClickListener.onClick(dialog, -5);
					}
				});

			}
			else
			{
				helpButton.setVisibility(View.GONE);
			}
			if(preview != null)
			{
				previewIV.setImageBitmap(preview);
				previewIV.setScaleType(ScaleType.CENTER_INSIDE);
			}
			else
			{
				previewIV.setVisibility(View.INVISIBLE);
			}
			if(errorText != null && !errorText.equals(""))
			{
				errorTextTV.setText(errorText);
			}
			else
			{
				errorTextTV.setVisibility(View.INVISIBLE);
			}
			
			// set the content message
			if (message != null)
			{
				messageTV.setText(message);
			}
			dialog.setContentView(layout);
			return dialog;
		}
	}
}