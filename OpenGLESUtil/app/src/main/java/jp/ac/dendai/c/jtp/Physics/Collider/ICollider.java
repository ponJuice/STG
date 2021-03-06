package jp.ac.dendai.c.jtp.Physics.Collider;

import android.graphics.Canvas;
import android.graphics.Paint;

import jp.ac.dendai.c.jtp.Physics.Physics.IPhysics2D;

/**
 * Created by Goto on 2016/06/28.
 */
public interface ICollider {
    public boolean isCollision(ICollider col);
    public IPhysics2D getPhysicsObject();
    public float getDeepMagnitude(ICollider col);
    public float getBoundaryCircle();
    public void debugOutlineDraw(Canvas canvas,Paint paint);
    public void setPhysicsObject(IPhysics2D obj);
    public float getRadius();
    public void setRadius(float radius);
    public void setMask(int mask);
    public int getMask();
    public int getTag();
    public void setTag(int tag);
    public boolean mask(ICollider col);
}
