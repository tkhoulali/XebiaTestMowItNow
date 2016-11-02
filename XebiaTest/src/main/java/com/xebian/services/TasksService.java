package com.xebian.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import com.xebian.impl.Tondeuse;

/**
 * @author TK
 * TasksService to manage execution of tasks
 */
public class TasksService {
	
	/**
	 * @param executor
	 * @param tasks
	 * @param indice: 
	 * @return : list of tondeuses after execution.
	 */
	public static List<Tondeuse> execute(final ExecutorService executor, List<Callable<Tondeuse>> tasks, String indice) {
		System.out.println(indice); // just to trace invocation source 
		
		// termination service
		CompletionService<Tondeuse> completionService = new ExecutorCompletionService<Tondeuse>(executor);

		// a list of Future to collect results 
		List<Future<Tondeuse>> futures = new ArrayList<Future<Tondeuse>>();
		
		List<Tondeuse> resultTondeuses = new ArrayList<Tondeuse>();
		
		Tondeuse resT = null;
		
		try {
			// passing tasks to the executor and getting the future
			for (Callable<Tondeuse> t : tasks) {
				futures.add(completionService.submit(t));
			}

			for (int i = 0; i < tasks.size(); ++i) {

				try {

					// get the first result available (future) with take()
					// and then we get the callable result with get().
					resT = completionService.take().get();
					if (resT != null) {
						resultTondeuses.add(resT);
						// we print the result task result
						System.out.println(resT.getPositionX() + "," + resT.getPositionY() + ","
								+ resT.getOrientation());
					}
				} catch (ExecutionException ee) {
					ee.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			executor.shutdown();
		}
		return resultTondeuses;
	}
	
}
