package com.example.dellpc.material2;

import org.opencv.core.Rect;

import java.util.Comparator;

class MyCompare
  implements Comparator<Rect>
{
  public int compare(Rect paramRect1, Rect paramRect2)
  { 

    return (int)(paramRect1.tl().x - paramRect2.tl().x);
  }
}


