package com.lubq.lm.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;




/**
 * widgetsツールクラス
 * 
 * @author wenyi <jhf@bestwiz.cn>
 * 
 * @copyright 2006, BestWiz(Dalian) Co.,Ltd
 * @version $Id: WidgetUtil.java,v 1.16 2007/04/14 07:56:37 liyan Exp $
 */
public class WidgetUtil {
    public static final int NUMBER_WITHOUT_MINUS_TYPE = 0;

    public static final int NUMRIC_WITHOUT_MINUS_TYPE = 1;

    public static final int NUMBER_WITH_MINUS_TYPE = 2;

    public static final int NUMRIC_WITH_MINUS_TYPE = 3;

    public static final int NUMBER_OR_CHAR_TYPE = 4;

    public static final int BOOKING_AMOUNT1_TYPE = 5;

    public static final int BOOKING_AMOUNT2_TYPE = 6;

    public static final int SPLIT_AMOUNT_TYPE = 7;

    public static final int PRICE_AMOUNT_TYPE = 8;

    public static final int ORDER_AMT_TYPE = 9;

    /**
     * Comboの初期化
     * 
     * @param cmb
     *            to be initialized
     * @param items
     *            to fill the combo
     */
    public static void initCombo(Object cmb, String[] items) {
        Validate.notNull(cmb);

        if (ArrayUtils.isEmpty(items))
            return;
        for (int i = 0; i < items.length; i++) {
            Class clazz = cmb.getClass();
            if (!(ClassUtils.isAssignable(clazz, Combo.class) || ClassUtils
                    .isAssignable(clazz, CCombo.class)))
                break;
            try {
                clazz.getDeclaredMethod("add", new Class[] { String.class })
                        .invoke(
                                cmb,
                                new Object[] { StringUtils
                                        .trimToEmpty(items[i]) });
            } catch (IllegalArgumentException e) {
//                DealerActivator.logException(e);
            } catch (SecurityException e) {
//                DealerActivator.logException(e);
            } catch (IllegalAccessException e) {
//                DealerActivator.logException(e);
            } catch (InvocationTargetException e) {
//                DealerActivator.logException(e);
            } catch (NoSuchMethodException e) {
//                DealerActivator.logException(e);
            }
        }
    }

    /**
     * TableColumnを取得する
     * 
     * @param table
     *            to be created on
     * @param alignment
     *            SWT.LEFT,SWT.CENTER,SWT.RIGHT
     * @param title
     *            column title
     * @param width
     *            column width
     * @param resizable
     *            true to allow resize, or false not
     * @return
     */
    public static TableColumn createTableColumn(Table table, int alignment,
            String title, int width, boolean resizable) {
        TableColumn tc = new TableColumn(table, alignment);
        tc.setText(title);
        tc.setResizable(resizable);
        tc.setWidth(width);
        return tc;
    }

    /**
     * TableColumnを取得する
     */
    public static TableColumn createTableColumn(Table table, int alignment,
            String title, int width) {
        return createTableColumn(table, alignment, title, width, true);
    }

    /**
     * TableColumnを取得する
     */
    public static TableColumn createTableColumn(Table table, String title,
            int width) {
        return createTableColumn(table, SWT.CENTER, title, width, true);
    }

    /**
     * 進捗バーでモニタイベントを追加
     * 
     * @param sc
     */
    public static void postProcessScrolledComposite(final ScrolledComposite sc) {
        sc.setExpandVertical(true);
        sc.setExpandHorizontal(true);
        sc.addControlListener(new ControlAdapter() {
            public void controlResized(ControlEvent e) {
                sc.setMinSize(sc.getContent().computeSize(SWT.DEFAULT,
                        SWT.DEFAULT));
            }
        });
    }

    /**
     * 
     * タイプによって音が違う: SoundEnum.DEALING：dealing shhet use SoundEnum.PRICE:price
     * panel use SoundEnum.ORDER:order sheet use（when order come）
     * SoundEnum.LOG:information viewer use SoundEnum.CALL_ORDER：order sheet
     * use（when rate reach）
     * 
     * @param type
     * @param time
     */
    /*public static void playAudio(final int type, final long time) {

        String path = GlobalConstants.RESOURCE_PATH + "sounds/";
        switch (type) {
        case SoundEnum.DEALING:
            path = path + SoundEnum.SOUND_DEALING_ENUM.getName();
            break;

        case SoundEnum.PRICE:
            path = path + SoundEnum.SOUND_PRICE_ENUM.getName();
            break;

        case SoundEnum.ORDER:
            path = path + SoundEnum.SOUND_ORDER_ENUM.getName();
            break;

        case SoundEnum.LOG:
            path = path + SoundEnum.SOUND_LOG_ENUM.getName();
            break;

        case SoundEnum.CALL_ORDER:
            path = path + SoundEnum.SOUND_CALLORDER_ENUM.getName();
            break;

        default:
            break;
        }

        final Clip clip;
        try {
            AudioInputStream stream = null;

            Map map = ApplicationContext.getContext().getPlayAudio();

            if (map == null) {
                map = new HashMap();
            }

            Object[] obj = (Object[]) map.get(path);
            if (obj != null && obj[0] != null && obj[1] != null
                    && obj[2] != null) {
                clip = (Clip) AudioSystem.getLine((DataLine.Info) obj[0]);

                // This method does not return until the audio file is
                // completely
                // loaded
                clip.open((AudioFormat) obj[1], (byte[]) obj[2], 0,
                        ((byte[]) obj[2]).length);
            } else {
                // From URL
                stream = AudioSystem.getAudioInputStream(WidgetUtil.class
                        .getResource(path));

                byte[] b = new byte[stream.available()];
                stream.read(b);

                // At present, ALAW and ULAW encodings must be converted
                // to PCM_SIGNED before it can be played
                AudioFormat format = stream.getFormat();
                if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
                    format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                            format.getSampleRate(), format
                                    .getSampleSizeInBits() * 2, format
                                    .getChannels(), format.getFrameSize() * 2,
                            format.getFrameRate(), true); // big
                    // endian
                    stream = AudioSystem.getAudioInputStream(format, stream);
                }

                // Create the clip
                DataLine.Info info = new DataLine.Info(Clip.class, stream
                        .getFormat(), ((int) stream.getFrameLength() * format
                        .getFrameSize()));
                clip = (Clip) AudioSystem.getLine(info);

                // This method does not return until the audio file is
                // completely
                // loaded
                clip.open(format, b, 0, b.length);
                obj = new Object[] { info, format, b };
                map.put(path, obj);
                ApplicationContext.getContext().setPlayAudio(map);
            }

            new Thread(new Runnable() {
                public void run() {
                    try {
                        clip.start();
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        DealerActivator.logException(e);
                    } finally {
                        if (clip != null) {
                            clip.close();
                        }
                    }
                }
            }).start();

            if (stream != null) {
                stream.close();
            }

        } catch (MalformedURLException e) {
            DealerActivator.logException(e);
        } catch (IOException e) {
            DealerActivator.logException(e);
        } catch (LineUnavailableException e) {
            DealerActivator.logException(e);
        } catch (UnsupportedAudioFileException e) {
            DealerActivator.logException(e);
        }
    }*/

    /**
     * Text入力のチェック
     * 
     * @param e
     * @param type:NUMBER_WITHOUT_MINUS_TYPE/NUMRIC_WITHOUT_MINUS_TYPE/
     */
    public static void verifyTextInput(VerifyEvent e, int type) {
        Pattern pattern = null;

        switch (type) {
        case NUMBER_WITHOUT_MINUS_TYPE:
            pattern = Pattern.compile("[0-9]\\d*");
            break;

        case NUMRIC_WITHOUT_MINUS_TYPE:
            pattern = Pattern.compile("[0-9.]\\d*");
            break;

        case NUMBER_WITH_MINUS_TYPE:
            pattern = Pattern.compile("[0-9-]\\d*");
            break;

        case NUMRIC_WITH_MINUS_TYPE:
            pattern = Pattern.compile("[0-9.-]\\d*");
            break;

        case NUMBER_OR_CHAR_TYPE:
            pattern = Pattern.compile("[0-9A-Za-z]\\d*");
            break;

        case BOOKING_AMOUNT1_TYPE:
            pattern = Pattern.compile("[0-9KMkm]\\d*");
            break;

        case ORDER_AMT_TYPE:
            pattern = Pattern.compile("[0-9KMkm]\\d*");
            break;

        case BOOKING_AMOUNT2_TYPE:
            pattern = Pattern.compile("[0-9.KMkm]\\d*");
            break;

        case PRICE_AMOUNT_TYPE:
            pattern = Pattern.compile("[0-9.kmKM]\\d*");
            break;

        case SPLIT_AMOUNT_TYPE:
            pattern = Pattern.compile("[0-9,]\\d*");
            break;

        default:
            break;
        }

        Matcher matcher = pattern.matcher(e.text);
        if (matcher.matches())
            e.doit = true;
        else if (e.text.length() > 0)
            e.doit = false;
        else
            e.doit = true;
    }

}