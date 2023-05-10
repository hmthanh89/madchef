using System;
using System.Diagnostics;
using OpenQA.Selenium;
using SeleniumCSharp.Core.DriverWrapper;

namespace SeleniumCSharp.Core.ElementWrapper
{
    /// <summary>
    ///     Declare functions for the element of type video.
    /// </summary>
    public class Video : BaseElement
    {
        /// <summary>
        ///     Find web element of type video using By locator.
        /// </summary>
        public Video(string locator)
            : base(locator)
        {
        }

        /// <summary>
        ///     Find web element of type video using By Xpath locator.
        /// </summary>
        public Video(By locator)
            : base(locator)
        {
        }


        /// <summary>
        ///     Hits play for the element of type video when the play button displays.
        /// </summary>
        public void Play()
        {
            var js = "arguments[0].play();";
            DriverUtils.ExecuteScript(js, GetElement());
        }

        /// <summary>
        ///     Hits pause for the element of type video.
        /// </summary>
        public void Pause()
        {
            var js = "arguments[0].pause();";
            DriverUtils.ExecuteScript(js, GetElement());
        }


        /// <summary>
        ///     Mutes the element of type video.
        /// </summary>
        public void Mute()
        {
            var js = "arguments[0].muted = true;";
            DriverUtils.ExecuteScript(js, GetElement());
        }

        /// <summary>
        ///     Unmutes the element of type video.
        /// </summary>
        public void Unmute()
        {
            var js = "arguments[0].muted = false;";
            DriverUtils.ExecuteScript(js, GetElement());
        }

        /// <summary>
        ///     Return the duration of the element of type video in seconds.
        /// </summary>
        public double GetDurationInSecond()
        {
            try
            {
                var js = "return arguments[0].duration;";
                var duration = DriverUtils.ExecuteScript(js, GetElement());
                if (duration != null)
                    return Convert.ToDouble(duration);
                throw new Exception("Couldn't get the element " + GetLocator() + " duration");
            }
            catch (Exception error)
            {
                throw error;
            }
        }

        /// <summary>
        ///     Return the duration of the element of type video.
        /// </summary>
        public string GetVideoDuration()
        {
            try
            {
                var duration = (float) GetDurationInSecond();
                var time = TimeSpan.FromSeconds(duration);

                return $"{time.TotalHours:00}:{time.Minutes:00}:{time.Seconds:00.0}";
            }
            catch (Exception error)
            {
                throw error;
            }
        }

        /// <summary>
        ///     Return the current time of the element of type video in seconds.
        /// </summary>
        public double GetCurrentTimeInSecond()
        {
            try
            {
                var js = "return arguments[0].currentTime;";
                var currentTime = DriverUtils.ExecuteScript(js, GetElement());
                if (currentTime != null)
                    return Convert.ToDouble(currentTime);
                throw new Exception("Couldn't get the element " + GetLocator() + " current time");
            }
            catch (Exception error)
            {
                throw error;
            }
        }

        /// <summary>
        ///     Return the status of the current element of type video.
        /// </summary>
        private int GetReadyState()
        {
            var js = "return arguments[0].readyState;";
            var status = DriverUtils.ExecuteScript(js, GetElement());

            return int.Parse(status.ToString());
        }

        /// <summary>
        ///     Wait for an element of type video to play till a specific range of waiting time.
        /// </summary>
        public void WaitForVideoEndedAt(int waitTime)
        {
            try
            {
                var stopwatch = Stopwatch.StartNew();
                while (GetReadyState() > 0 && stopwatch.Elapsed <= TimeSpan.FromSeconds(waitTime)) ;
            }
            catch (Exception error)
            {
                throw error;
            }
        }

        /// <summary>
        ///     Wait for an element of type video to play in a specific range of time.
        ///     Default timeOut = 60 if it not set.
        /// </summary>
        public void WaitForVideoPlayed(int timeOutInSecond = 1)
        {
            try
            {
                var stopwatch = Stopwatch.StartNew();
                while (GetCurrentTimeInSecond() <= 0 && stopwatch.Elapsed <= TimeSpan.FromSeconds(timeOutInSecond)) ;
            }
            catch (Exception error)
            {
                throw error;
            }
        }

        /// <summary>
        ///     Wait for an element of type video to load in a specific range of time.
        ///     Default timeOut = 60 if it not set.
        /// </summary>
        public void WaitForVideoLoad(int timeOutInSecond = 1)
        {
            try
            {
                var stopwatch = Stopwatch.StartNew();
                while (GetReadyState() < 4 && stopwatch.Elapsed <= TimeSpan.FromSeconds(timeOutInSecond)) ;
            }
            catch (Exception error)
            {
                throw error;
            }
        }
    }
}