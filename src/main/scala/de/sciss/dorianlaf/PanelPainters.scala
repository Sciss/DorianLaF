package de.sciss.dorianlaf

import javax.swing.plaf.synth.{SynthContext, SynthPainter}
import javax.swing.UIManager
import javax.imageio.ImageIO
import java.awt._

object PanelBackgroundPainter {
   lazy val pntCanvas = {
      val imgGray = ImageIO.read( PanelBackgroundPainter.getClass.getResourceAsStream( "canvas.png" ))
      val w = imgGray.getWidth
      val h = imgGray.getHeight
      val imgARGB = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
         .getDefaultConfiguration().createCompatibleImage( w, h )
      val g = imgARGB.createGraphics()
      g.drawImage( imgGray, 0, 0, null )
      g.dispose()
      new TexturePaint( imgARGB, new Rectangle( 0, 0, w, h ))
   }

   lazy val cmpCanvas = new ApplyCanvas
}

class PanelBackgroundPainter extends SynthPainter {
   import PanelBackgroundPainter._
   
   override def paintPanelBackground( context: SynthContext, g: Graphics, x: Int, y: Int, w: Int, h: Int ) {
      val start   = UIManager.getColor( "Panel.startBackground" )
      val end     = UIManager.getColor( "Panel.endBackground" )
      val g2      = g.asInstanceOf[ Graphics2D ]
      val pntGrad = new GradientPaint( x, y, start, w, h, end )
      g2.setPaint( pntGrad )
      g2.fillRect( x, y, w, h )

      g2.setPaint( pntCanvas )
      val cmpOrig = g2.getComposite
      g2.setComposite( cmpCanvas )
      g2.fillRect( x, y, w, h )
      g2.setComposite( cmpOrig )

//      g2.setPaint( null )
//      g2.setColor( new Color( 255, 255, 255, 120 ))
//      g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON )
//      val arc2d   = new CubicCurve2D.Double( 0, h/4, w/3, h/10, 66 * w, 1.5 * h, w, h/8 )
//      g2.draw( arc2d )
//      g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF )
   }
}