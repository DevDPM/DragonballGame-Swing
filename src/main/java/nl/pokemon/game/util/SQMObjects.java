package nl.pokemon.game.util;

import nl.pokemon.game.enums.Destination;
import nl.pokemon.game.model.BasePortalSQM;
import nl.pokemon.game.model.BaseSQM;
import nl.pokemon.game.model.SQMObjects.ViewSQM;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SQMObjects {

    public static final Map<Integer, BaseSQM> SQMObjects = new HashMap<>();
    private static ClassLoader startSQMObjects;


    public static BaseSQM convertFDM_XY_ToSQM(Destination destination, int x, int y) {
        int[][] dataMap = destination.getMap();

        if (x < 0 || y < 0 || x > destination.getMap()[1].length-1 || y > destination.getMap().length-1) {
            return null;
        }

        BaseSQM baseSQM = SQMObjects.get(dataMap[y][x]);

        if (baseSQM instanceof BasePortalSQM basePortalSQM) {
            Object[] xyDestination = Portals.getDestinationXYByPortalXY(x, y, destination);
            basePortalSQM.setDestinationFDMIndexXY((Integer) xyDestination[0], (Integer) xyDestination[1],(Destination) xyDestination[2],(Destination) xyDestination[3]);
            return basePortalSQM;
        }

        return baseSQM;
    }

    public static void bootstrap() {
        Class<?> startFile = ViewSQM.class;
        startSQMObjects = startFile.getClassLoader();
        String rootPackagePath = startFile.getPackageName(); // get package path of startClass
        loadClassPaths(rootPackagePath);
    }

    private static void loadClassPaths(String packagePath) {
        String path = packagePath.replace('.', '/');
        URL url = startSQMObjects.getResource(path);

        if (url == null)
            throw new NullPointerException("Could not get URL from ClassLoader (= null)");

        File folder = new File(url.getPath());
        File[] fileArray = folder.listFiles(); // get array of file names (class or package) from within the root package path

        if (fileArray == null)
            throw new NullPointerException("FileArray did not retrieve any paths (= null)");

        convertPathToClass(packagePath, fileArray);
    }

    private static void convertPathToClass(String startPackagePath, File[] fileArray) {
        for (int i = 0; i < fileArray.length; i++) {
            String fileName = fileArray[i].getName();
            int index = fileName.indexOf(".");

            if (fileName.contains("$"))
                continue;

            if (!fileName.contains(".class")) {
                String childPath = startPackagePath + "." + fileName;
                loadClassPaths(childPath);
                continue;
            }

            String className = fileName.substring(0, index);
            String classNamePath = startPackagePath + "." + className;
            try {
                Class<?> foundClass = Class.forName(classNamePath);
                BaseSQM instanceBaseSQM = (BaseSQM) foundClass.getConstructor(new Class[]{}).newInstance();
                SQMObjects.put(instanceBaseSQM.getObjectNumber(), instanceBaseSQM);


            } catch (ClassNotFoundException ex) {
                ex.getStackTrace();
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
