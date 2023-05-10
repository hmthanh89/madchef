using System;
using System.Threading;
using System.Threading.Tasks;

namespace SeleniumCSharp.Core.Helpers
{
    /// <summary>
    ///     Contains waiting methods
    /// </summary>
    public static class WaitHelper
    {
        /// <summary>
        ///     Wait for a condition with given timeout and timeInterval.
        /// </summary>
        /// <param name="condition"></param>
        /// <param name="timeout"></param>
        /// <param name="sleepInterval"></param>
        /// <returns></returns>
        public static bool WaitUntil(Func<bool> condition, TimeSpan timeout, TimeSpan sleepInterval)
        {
            var start = DateTime.Now;
            Task<bool> syncTask;
            while ((DateTime.Now - start).TotalSeconds < timeout.TotalSeconds)
            {
                syncTask = new Task<bool>(() =>
                {
                    Console.WriteLine("WaitHelper::WaitUntil() executing on thread {0}",
                        Thread.CurrentThread.ManagedThreadId);
                    return condition();
                });
                syncTask.RunSynchronously();
                if (syncTask.IsCompleted && syncTask.Result) return true;
                Thread.Sleep(sleepInterval);
            }

            return false;
        }
    }
}