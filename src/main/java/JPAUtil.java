
/*
package dao;

import excecao.InfraestruturaException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JPAUtil
{	private static EntityManagerFactory emf;
	private static final ThreadLocal<EntityManager> threadEntityManager = 
			new ThreadLocal<EntityManager>();
	private static final ThreadLocal<EntityTransaction> threadTransaction = 
			new ThreadLocal<EntityTransaction>();
	public static ThreadLocal<Integer> semaforo = new ThreadLocal<Integer>();
	
	static 
	{	try
		{	emf = Persistence.createEntityManagerFactory("dao");
		}
		catch(Throwable e)
		{	
			e.printStackTrace();
			System.out.println(">>>>>>>>>> Mensagem de erro: " + e.getMessage());
		}
	}

	public static void beginTransaction()
	{	//System.out.println("Vai criar transacao");

		EntityTransaction tx = threadTransaction.get();
		if(semaforo.get()==null)
			semaforo.set(1);
		else
			semaforo.set(semaforo.get()+1);
		try
		{
			if (tx == null)
			{	tx = getEntityManager().getTransaction();
				tx.begin();
				threadTransaction.set(tx);
				//System.out.println("Criou transacao");
			}
			else
			{	//System.out.println("Nao criou transacao");
			}

		}
		catch (RuntimeException ex)
		{	throw new InfraestruturaException(ex);
		}
	}
	public static EntityManager getEntityManager()
	{	// System.out.println("Abriu ou recuperou sessão");

		EntityManager s = threadEntityManager.get();
		// Abre uma nova Sessão, se a thread ainda não possui uma.
		try
		{	if (s == null)
		{	s = emf.createEntityManager();
			threadEntityManager.set(s);
			//System.out.println("criou sessao");
		}
		}
		catch (RuntimeException ex)
		{	throw new InfraestruturaException(ex);
		}
		return s;
	}

	public static void commitTransaction()
	{	EntityTransaction tx = threadTransaction.get();
		try
		{	if( semaforo.get() == 1){
			if ( tx != null && tx.isActive())
			{	tx.commit();
				System.out.println("Comitou transacao");
			}
			threadTransaction.set(null);
		}
			if(!semaforo.equals(null)) {
				semaforo.set(semaforo.get() - 1);
				if(semaforo.get() == 0)
					semaforo.set(null);
			}
		}
		catch (RuntimeException ex)
		{	try
		{	rollbackTransaction();
		}
		catch(RuntimeException e)
		{}

			throw new InfraestruturaException(ex);
		}
	}

	public static void rollbackTransaction()
	{	System.out.println("Vai efetuar rollback de transacao");

		EntityTransaction tx = threadTransaction.get();
		try
		{	threadTransaction.set(null);
			if ( tx != null && tx.isActive())
			{	tx.rollback();
			}
		}
		catch (RuntimeException ex)
		{	throw new InfraestruturaException(ex);
		}
		finally
		{	closeEntityManager();
		}
	}

	public static void closeEntityManager()
	{	//System.out.println("Vai fechar sessão");

		try
		{	if(semaforo.get() == null) {
			EntityManager s = threadEntityManager.get();
			threadEntityManager.set(null);
			if (s != null && s.isOpen())
			{	s.close();
				//System.out.println("Fechou a sessão");
			}

			EntityTransaction tx = threadTransaction.get();
			if ( tx != null && tx.isActive())
			{	rollbackTransaction();
				throw new RuntimeException("EntityManager sendo fechado " +
						"com transação ativa.");
			}
		}
		}
		catch (RuntimeException ex)
		{	throw new InfraestruturaException(ex);
		}
	}
}
*/
