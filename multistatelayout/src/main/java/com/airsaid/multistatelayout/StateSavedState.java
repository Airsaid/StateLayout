package com.airsaid.multistatelayout;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/**
 * @author airsaid
 */
class StateSavedState extends View.BaseSavedState {
  public int currentStateId;

  public StateSavedState(Parcel source) {
    super(source);
    currentStateId = source.readInt();
  }

  public StateSavedState(Parcelable superState) {
    super(superState);
  }

  @Override
  public void writeToParcel(Parcel out, int flags) {
    super.writeToParcel(out, flags);
    out.writeInt(currentStateId);
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
