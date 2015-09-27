package it.polimi.provaFinale2013.graphics;

import it.polimi.provaFinale2013.exceptions.AlreadyArrivedException;
import it.polimi.provaFinale2013.exceptions.NotStartedException;
import it.polimi.provaFinale2013.model.BetContainer;
import it.polimi.provaFinale2013.model.Track;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * This Panel manages the horses race and their movements on the track.
 */
public class TrackWindow extends JPanel {

	private static final long serialVersionUID = -4544114149315404173L;

	private static final int deltaTime = 600;

	/**
	 * Constant in order to display horses and bet position on track
	 */
	private static final int DELTA_Y = 25;
	private static final int BET_Y_POSITION = 150;

	/**
	 * Contains the horses token images
	 */
	private List<Image> horsesImage;

	/**
	 * Contains the horses and bets point inside an interpolator
	 */
	private List<Interpolator<Object>> horsesPoint;
	private List<Interpolator<BetContainer>> betsPoint;

	private Font font = null;

	/**
	 * Constructor of the TrackWindow.
	 */
	public TrackWindow() {
		setOpaque(false);
		horsesImage = new ArrayList<Image>();
		horsesPoint = new ArrayList<Interpolator<Object>>();
		betsPoint = new ArrayList<Interpolator<BetContainer>>();
		setSize(376, 332);

		try {
			InputStream is = getClass().getResourceAsStream("/edmunds.ttf");
			font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
		} catch (IOException e) {
		}

		for (int i = 0; i < 6; i++) {
			horsesPoint.add(new Interpolator<Object>(baseX(i, baseY()), baseY(), null));
		}

		try {
			horsesImage.add(ImageIO.read(getClass().getResource(
					"/FamilyGame/SegnaliniQuotazioniScuderie/SegnalinoQuotazioniScuderiaNeraArrotonato.png")));
			horsesImage.add(ImageIO.read(getClass().getResource(
					"/FamilyGame/SegnaliniQuotazioniScuderie/SegnalinoQuotazioniScuderiaBluArrotondato.png")));
			horsesImage.add(ImageIO.read(getClass().getResource(
					"/FamilyGame/SegnaliniQuotazioniScuderie/SegnalinoQuotazioniScuderiaVerdeArrotondato.png")));
			horsesImage.add(ImageIO.read(getClass().getResource(
					"/FamilyGame/SegnaliniQuotazioniScuderie/SegnalinoQuotazioniScuderiaRossaArrotondato.png")));
			horsesImage.add(ImageIO.read(getClass().getResource(
					"/FamilyGame/SegnaliniQuotazioniScuderie/SegnalinoQuotazioniScuderiaGiallaArrotondato.png")));
			horsesImage.add(ImageIO.read(getClass().getResource(
					"/FamilyGame/SegnaliniQuotazioniScuderie/SegnalinoQuotazioniScuderiaBiancaArrotondato.png")));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			/**
			 * Runs the action of the timer task.
			 */
			public void run() {
				repaint();
			}
		}, 0, 20);
	}

	/**
	 * Overrides the paintComponent method to paint the horses images in their proper position.
	 */
	@Override
	public synchronized void paintComponent(Graphics g) {
		//Draw the six images of the horses
		for (int i = 0; i < horsesPoint.size(); i++) {
			Image image = horsesImage.get(i);
			Interpolator<Object> interpolator = horsesPoint.get(i);
			g.drawImage(image, interpolator.getXPosition(), interpolator.getYPosition(), 25, 25, null);
		}

		//Draw the images of the bets
		for (Interpolator<BetContainer> b : betsPoint) {
			Image image = b.getObject().getBet().getBetToken().getImage();
			Interpolator<BetContainer> interpolator = b;
			g.drawImage(image, interpolator.getXPosition(), interpolator.getYPosition(), 40, 40, null);
			String danari = String.format("%d$", b.getObject().getBet().getValue());

			try {
				g.setColor(Color.BLACK);
				g.setFont(font.deriveFont(15F));
			} catch (NullPointerException e) {
				//Font not loaded
			}

			g.drawString(b.getObject().getPlayerName(), interpolator.getXPosition(), interpolator.getYPosition() + 51);
			g.drawString(danari, interpolator.getXPosition(), interpolator.getYPosition() + 65);
		}
		super.paintComponent(g);
	}

	/**
	 * Updates horses position of 1 step.
	 * 
	 * @param track
	 */
	public synchronized void updateHorses(Track track) {
		for (int i = 0; i < 6; i++) {
			try {
				//Calculate the new point for the horse i+1
				int horsePosition = track.getHorseRacePosition(i + 1);
				horsesPoint.get(i).setNewPosition(baseX(i, baseY() - DELTA_Y * horsePosition), baseY() - DELTA_Y * horsePosition, deltaTime);
			} catch (NotStartedException e) {
				horsesPoint.get(i).setNewPosition(baseX(i, baseY()), baseY(), deltaTime);
			} catch (AlreadyArrivedException e) {
				horsesPoint.get(i).setNewPosition(baseX(i, baseY() - DELTA_Y * Track.FINAL_POSITION), baseY() - DELTA_Y * Track.FINAL_POSITION,
						deltaTime);
			}
			horsesPoint.get(i).start();
		}
		repaint();
	}

	/**
	 * Computes the interpolation of the new bets.
	 * 
	 * @param bets
	 */
	public synchronized void updateBets(List<BetContainer> bets) {

		//Add bets not in the list betsPoint
		for (BetContainer betContainer : bets) {
			boolean found = false;
			for (Interpolator<BetContainer> b : betsPoint) {
				if (b.getObject().equals(betContainer)) {
					found = true;
				}
			}
			if (!found) {
				betsPoint.add(new Interpolator<BetContainer>(baseX(betContainer.getBet().getBetToken().getStable() - 1, BET_Y_POSITION) - 10,
						BET_Y_POSITION, betContainer));
			}
		}

		//Remove bets in the list betsPoint that are not in bets
		Iterator<Interpolator<BetContainer>> bIter = betsPoint.iterator();
		while (bIter.hasNext()) {
			Interpolator<BetContainer> b = bIter.next();
			boolean found = false;
			for (BetContainer betContainer : bets) {
				if (b.getObject().equals(betContainer)) {
					found = true;
				}
			}
			if (!found) {
				bIter.remove();
			}
		}

		//Check if there's more than one interpolator overlapped and move them
		boolean overlap = false;
		do {
			overlap = false;
			for (Interpolator<BetContainer> i1 : betsPoint) {
				for (Interpolator<BetContainer> i2 : betsPoint) {
					if (i1 != i2 && Math.abs(i1.destination.x - i2.destination.x) < 20 && Math.abs(i1.destination.y - i2.destination.y) < 45) {
						i1.setNewPosition(i1.destination.x, i1.destination.y + 30, 1000);
						i2.setNewPosition(i2.destination.x, i2.destination.y - 30, 1000);
						i1.start();
						i2.start();
						overlap = true;
					}
				}
			}
		} while (overlap);
	}
	
	/**
	 * Delete all graphics bets
	 */
	public synchronized void deleteBets() {
		betsPoint.clear();
	}

	//Computation function

	/**
	 * Calculates the x-coordinate of the stable.
	 * 
	 * @param stable the stable [0..5]
	 * @return the x coordinate
	 */
	private int baseX(int stable, int y) {
		int xOffset = 10;
		switch (stable) {
		case 0:
			return (int) (20 + xOffset + ((double) y - 300) / ((double) 4 - 300) * (72 - 20 - xOffset));
		case 1:
			return (int) (82 + xOffset + ((double) y - 300) / ((double) 4 - 300) * (110 - 82 - xOffset));
		case 2:
			return (int) (146 + xOffset + ((double) y - 300) / ((double) 4 - 300) * (152 - 146 - xOffset));
		case 3:
			return (int) (206 + xOffset + ((double) y - 300) / ((double) 4 - 300) * (192 - 206 - xOffset));
		case 4:
			return (int) (266 + xOffset + ((double) y - 300) / ((double) 4 - 300) * (234 - 266 - xOffset));
		case 5:
			return (int) (324 + xOffset + ((double) y - 300) / ((double) 4 - 300) * (274 - 324 - xOffset));
		}
		return 0;
	}

	/**
	 * 
	 * @return the constant basey = 297
	 */
	private int baseY() {
		return 300;
	}

	/**
	 * The interpolator of points.
	 * 
	 * @param <T>
	 */
	static class Interpolator<T> {
		private Point source;
		private Point destination;
		//Time to complete the movement
		private long time; //In millis
		private long initTime;
		private T object;

		/**
		 * Constructor of the interpolator.
		 * 
		 * @param sX
		 * @param sY
		 * @param object
		 */
		public Interpolator(int sX, int sY, T object) {
			source = new Point(sX, sY);
			destination = new Point(sX, sY);
			time = 0;
			initTime = 0;
			this.object = object;
		}

		/**
		 * Returns the graphic object associated with this interpolator.
		 * 
		 * @return the graphic object
		 */
		public T getObject() {
			return object;
		}

		/**
		 * Sets the new position of the graphic component.
		 * 
		 * @param dX new x (absolute)
		 * @param dY new y (absolute)
		 * @param time the duration of the animation
		 */
		public void setNewPosition(int dX, int dY, int time) {
			source = new Point(getXPosition(), getYPosition());
			destination = new Point(dX, dY);
			this.time = time;
		}

		/**
		 * Starts the interpolation.
		 */
		public void start() {
			initTime = System.currentTimeMillis();
		}

		/**
		 * 
		 * @return the X position
		 */
		public int getXPosition() {
			long deltaTime = System.currentTimeMillis() - initTime;
			if (deltaTime >= time) {
				return destination.x;
			}
			return source.x + (int) (((double) destination.x - source.x) * ((double) deltaTime / time));
		}

		/**
		 * 
		 * @return the Y position
		 */
		public int getYPosition() {
			long deltaTime = System.currentTimeMillis() - initTime;
			if (deltaTime >= time) {
				return destination.y;
			}
			return source.y + (int) (((double) destination.y - source.y) * ((double) deltaTime / time));
		}

		/**
		 * 
		 * @return false if finished
		 */
		public boolean finished() {
			long deltaTime = System.currentTimeMillis() - initTime;
			if (deltaTime < time) {
				return true;
			} else {
				return false;
			}
		}

	}

}
