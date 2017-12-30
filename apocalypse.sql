-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Mar 26 Décembre 2017 à 20:24
-- Version du serveur :  10.1.16-MariaDB
-- Version de PHP :  7.0.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `apocalypse`
--

-- --------------------------------------------------------

--
-- Structure de la table `annonce`
--

CREATE TABLE `annonce` (
  `id_annonce` int(10) NOT NULL,
  `id_utilisateur` int(10) NOT NULL,
  `titre` varchar(50) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `capacite_max` int(10) DEFAULT NULL,
  `date_creation` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `commentaire`
--

CREATE TABLE `commentaire` (
  `id_commentaire` int(11) NOT NULL,
  `id_reservation` int(11) NOT NULL,
  `id_utilisateur` int(11) NOT NULL,
  `commentaire` varchar(1000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `etat_reservation`
--

CREATE TABLE `etat_reservation` (
  `id_etat` int(10) NOT NULL,
  `libelle` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `etat_reservation`
--

INSERT INTO `etat_reservation` (`id_etat`, `libelle`) VALUES
(1, 'En attente de confirmation'),
(2, 'Hotelier a confirme'),
(3, 'En attente de validation'),
(4, 'Validee');

-- --------------------------------------------------------

--
-- Structure de la table `reservation`
--

CREATE TABLE `reservation` (
  `id_reservation` int(10) NOT NULL,
  `id_annonce` int(10) NOT NULL,
  `id_utilisateur` int(10) NOT NULL,
  `prix` double NOT NULL,
  `capacite_max` int(10) NOT NULL,
  `date_sejour` datetime NOT NULL,
  `id_statut_reservation` int(10) NOT NULL,
  `id_etat_reservation` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `statut_reservation`
--

CREATE TABLE `statut_reservation` (
  `id_statut_reservation` int(10) NOT NULL,
  `libelle` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `statut_reservation`
--

INSERT INTO `statut_reservation` (`id_statut_reservation`, `libelle`) VALUES
(1, 'Terminee'),
(2, 'En cours'),
(3, 'A venir');

-- --------------------------------------------------------

--
-- Structure de la table `type_utilisateur`
--

CREATE TABLE `type_utilisateur` (
  `id_type_utilisateur` int(10) NOT NULL,
  `libelle` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `type_utilisateur`
--

INSERT INTO `type_utilisateur` (`id_type_utilisateur`, `libelle`) VALUES
(1, 'Administrateur'),
(2, 'Hotelier'),
(3, 'Standard');

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `id_utilisateur` int(10) NOT NULL,
  `id_type_utilisateur` int(10) NOT NULL,
  `nom` varchar(50) DEFAULT NULL,
  `prenom` varchar(50) DEFAULT NULL,
  `mail` varchar(70) DEFAULT NULL,
  `mobile` varchar(15) DEFAULT NULL,
  `adresse` varchar(50) DEFAULT NULL,
  `code_postal` varchar(10) DEFAULT NULL,
  `ville` varchar(50) DEFAULT NULL,
  `point_bonus` int(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id_utilisateur`, `id_type_utilisateur`, `nom`, `prenom`, `mail`, `mobile`, `adresse`, `code_postal`, `ville`, `point_bonus`) VALUES
(1, 1, 'LIM', 'Sindy', 'sindy.lim91@gmail.com', '0635267495', '100 rue des tests', '75015', 'Paris', 0),
(2, 2, 'HAKANJIN', 'Romeo', 'romeo.hakanjin96@gmail.com', '0635987465', '12 allee des test', '95800', 'CERGY', 0),
(3, 3, 'DIAGNE', 'Massamba', 'massdinho10@gmail.com', '0745873695', '3 avenue de test', '93600', 'AUNAY-SOUS-BOIS', 0);

--
-- Index pour les tables exportées
--

--
-- Index pour la table `annonce`
--
ALTER TABLE `annonce`
  ADD PRIMARY KEY (`id_annonce`),
  ADD KEY `FK1_ANNONCE` (`id_utilisateur`);

--
-- Index pour la table `commentaire`
--
ALTER TABLE `commentaire`
  ADD PRIMARY KEY (`id_commentaire`),
  ADD KEY `FK2_COMMENTAIRE` (`id_utilisateur`);

--
-- Index pour la table `etat_reservation`
--
ALTER TABLE `etat_reservation`
  ADD PRIMARY KEY (`id_etat`);

--
-- Index pour la table `reservation`
--
ALTER TABLE `reservation`
  ADD PRIMARY KEY (`id_reservation`),
  ADD KEY `FK1_RESERVATION` (`id_annonce`),
  ADD KEY `FK2_RESERVATION` (`id_utilisateur`),
  ADD KEY `FK3_RESERVATION` (`id_etat_reservation`),
  ADD KEY `FK4_RESERVATION` (`id_statut_reservation`);

--
-- Index pour la table `statut_reservation`
--
ALTER TABLE `statut_reservation`
  ADD PRIMARY KEY (`id_statut_reservation`);

--
-- Index pour la table `type_utilisateur`
--
ALTER TABLE `type_utilisateur`
  ADD PRIMARY KEY (`id_type_utilisateur`);

--
-- Index pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`id_utilisateur`),
  ADD KEY `FK1_UTILISATEUR` (`id_type_utilisateur`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `annonce`
--
ALTER TABLE `annonce`
  MODIFY `id_annonce` int(10) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `commentaire`
--
ALTER TABLE `commentaire`
  MODIFY `id_commentaire` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `etat_reservation`
--
ALTER TABLE `etat_reservation`
  MODIFY `id_etat` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT pour la table `statut_reservation`
--
ALTER TABLE `statut_reservation`
  MODIFY `id_statut_reservation` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT pour la table `type_utilisateur`
--
ALTER TABLE `type_utilisateur`
  MODIFY `id_type_utilisateur` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `id_utilisateur` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `annonce`
--
ALTER TABLE `annonce`
  ADD CONSTRAINT `FK1_ANNONCE` FOREIGN KEY (`id_utilisateur`) REFERENCES `utilisateur` (`id_utilisateur`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `commentaire`
--
ALTER TABLE `commentaire`
  ADD CONSTRAINT `FK2_COMMENTAIRE` FOREIGN KEY (`id_utilisateur`) REFERENCES `utilisateur` (`id_utilisateur`);

--
-- Contraintes pour la table `reservation`
--
ALTER TABLE `reservation`
  ADD CONSTRAINT `FK1_RESERVATION` FOREIGN KEY (`id_annonce`) REFERENCES `annonce` (`id_annonce`),
  ADD CONSTRAINT `FK2_RESERVATION` FOREIGN KEY (`id_utilisateur`) REFERENCES `utilisateur` (`id_utilisateur`),
  ADD CONSTRAINT `FK3_RESERVATION` FOREIGN KEY (`id_etat_reservation`) REFERENCES `etat_reservation` (`id_etat`),
  ADD CONSTRAINT `FK4_RESERVATION` FOREIGN KEY (`id_statut_reservation`) REFERENCES `statut_reservation` (`id_statut_reservation`);

--
-- Contraintes pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD CONSTRAINT `FK1_UTILISATEUR` FOREIGN KEY (`id_type_utilisateur`) REFERENCES `type_utilisateur` (`id_type_utilisateur`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
