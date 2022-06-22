package com.airsaid.statelayout;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/**
 * @author airsaid
 */
class StateSavedState extends View.BaseSavedState {
  public Class<? extends State> currentStateClass;

  @SuppressWarnings("unchecked")
  public StateSavedState(Parcel source) {
    super(source);
    currentStateClass = (Class<? extends State>) source.readSerializable();
  }

  public StateSavedState(Parcelable superState) {
    super(superState);
  }

  @Override
  public void writeToParcel(Parcel out, int flags) {
    super.writeToParcel(out, flags);
    out.writeSerializable(currentStateClass);
  }

  public static final Creator<StateSavedState> CREATOR =
      new Creator<StateSavedState>() {

        @Override
        public StateSavedState createFromParcel(Parcel source) {
          return new StateSavedState(source);
        }

        @Override
        public StateSavedState[] newArray(int size) {
          return new StateSavedState[size];
        }
      };
}
