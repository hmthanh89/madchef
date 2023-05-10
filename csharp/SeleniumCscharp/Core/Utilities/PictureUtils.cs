using System;
using System.Drawing;
using System.Drawing.Imaging;
using System.IO;
using OpenQA.Selenium;
using SeleniumCSharp.Core.DriverWrapper;
using SeleniumCSharp.Core.ElementWrapper;

namespace SeleniumCSharp.Core.Utilities
{
    /// <summary>
    ///     This class is used to compare pictures, capture screenshot, and capture element screenshot
    /// </summary>
    public class PictureUtils
    {
        /// <summary>
        ///     compare two images, return (percent = diff * 100), and highlight the different if both pics are not the same
        ///     (baseImgPath , compareImgPath , outPutPath)
        /// </summary>
        public static float ComparePictureByPercent(string imagePath1, string imagePath2, string outPutImage)
        {
            var differentPixels = 0;

            var baseImage = new Bitmap(imagePath1);
            var compareImage = new Bitmap(imagePath2);
            if (baseImage.Size != compareImage.Size) return 100;
            for (var i = 0; i < baseImage.Width; ++i)
            for (var j = 0; j < baseImage.Height; ++j)
            {
                var secondColor = compareImage.GetPixel(i, j);
                var firstColor = baseImage.GetPixel(i, j);

                if (firstColor != secondColor) differentPixels++;
            }

            if (differentPixels > 0)
            {
                var container =
                    HighlightDifferentPixels(baseImage, compareImage, baseImage.Width, baseImage.Height);
                container.Save(outPutImage, ImageFormat.Jpeg);
            }

            var totalPixels = baseImage.Width * baseImage.Height;
            var diff = differentPixels / (float) totalPixels;
            return diff * 100;

        }

        private static Bitmap HighlightDifferentPixels(Bitmap baseImage, Bitmap compareImage, int w, int h)
        {
            var container = compareImage;
            var coordinate = new bool[w, h];

            for (var i = 1; i < baseImage.Width; i++)
            for (var j = 1; j < baseImage.Height; j++)
            {
                var secondColor = compareImage.GetPixel(i, j);
                var firstColor = baseImage.GetPixel(i, j);

                if (firstColor != secondColor)
                {
                    int rectMaxWidth = i + 20,
                        rectMinWidth = i - 20,
                        rectMaxheight = j + 20,
                        rectMinheight = j - 20;

                    if (rectMaxWidth >= baseImage.Width) rectMaxWidth = baseImage.Width - 2;
                    if (rectMinWidth <= 0) rectMinWidth = 2;
                    if (rectMaxheight >= baseImage.Height) rectMaxheight = baseImage.Height - 2;
                    if (rectMinheight <= 0) rectMinheight = 2;

                    for (w = rectMinWidth; w < rectMaxWidth; ++w)
                    for (h = rectMinheight; h < rectMaxheight; ++h)
                        coordinate[w, h] = true;
                }
            }

            for (var i = 1; i < baseImage.Width - 1; i++)
            for (var j = 1; j < baseImage.Height - 1; j++)
                if (coordinate[i, j])
                    if (coordinate[i - 1, j] == false || coordinate[i + 1, j] == false ||
                        coordinate[i, j - 1] == false || coordinate[i, j + 1] == false)
                    {
                        container.SetPixel(i - 1, j, Color.Red);
                        container.SetPixel(i + 1, j, Color.Red);
                        container.SetPixel(i, j, Color.Red);
                        container.SetPixel(i, j + 1, Color.Red);
                        container.SetPixel(i, j - 1, Color.Red);
                    }

            return container; // replace 
        }

        /// <summary>
        ///     Capture full screenshot and save it as .jpeg file in specified file path.
        /// </summary>
        public static string CaptureScreenshot(string fileName, string filePath)
        {
            try
            {
                var screenShot = ((ITakesScreenshot) DriverUtils.GetDriver()).GetScreenshot();

                if (!Directory.Exists(filePath))
                    Directory.CreateDirectory(filePath);

                var destinationPath = filePath + "/" + fileName;

                if (!fileName.Contains(".jpeg"))
                    destinationPath = destinationPath + ".jpeg";

                screenShot.SaveAsFile(destinationPath, ScreenshotImageFormat.Jpeg);

                return destinationPath;
            }
            catch (Exception error)
            {
                throw error;
            }
        }

        /// <summary>
        ///     Capture screenshot of a selected element and save it as .jpeg file in specified file path.
        /// </summary>
        public static string ElementScreenshot(BaseElement element, string fileName, string filePath)
        {
            try
            {
                //element.WaitForDisplay();

                if (!element.IsDisplayed()) throw new Exception("Couldn't capture element " + element.GetLocator());
                var destinationPath = CaptureScreenshot(fileName, filePath);

                var image = Image.FromFile(destinationPath);

                var rect = new Rectangle(element.GetPointX(), element.GetPointY(), element.GetWidth(),
                    element.GetHeight());

                var originalImage = new Bitmap(image);
                var croppedImage = originalImage.Clone(rect, originalImage.PixelFormat);

                image.Dispose();
                originalImage.Dispose();

                croppedImage.Save(destinationPath, ImageFormat.Jpeg);

                return destinationPath;

            }
            catch (Exception error)
            {
                throw error;
            }
        }
    }
}